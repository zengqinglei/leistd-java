package com.mysoft.permission.user.appservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mysoft.emailclient.EmailClient;
import com.mysoft.enterpriseclient.EnterpriseClient;
import com.mysoft.enterpriseclient.dto.EnterpriseInfoResDTO;
import com.mysoft.identityclient.IdentityClient;
import com.mysoft.identityclient.dto.CreateUserReqDTO;
import com.mysoft.identityclient.dto.CreateUserResDTO;
import com.mysoft.identityclient.dto.UserResDTO;
import com.mysoft.leistd.domain.entities.page.PageQuery;
import com.mysoft.leistd.domain.entities.page.PageResult;
import com.mysoft.leistd.domain.repositories.HasKeyRepository;
import com.mysoft.leistd.dto.model.PageResDTO;
import com.mysoft.leistd.exception.BadRequestException;
import com.mysoft.leistd.exception.NotFoundErrorException;
import com.mysoft.leistd.security.CurrentUser;
import com.mysoft.permission.common.properties.IdentityServerProps;
import com.mysoft.permission.common.properties.PermissionEmailProps;
import com.mysoft.permission.common.utils.AuthUtils;
import com.mysoft.permission.role.entity.EnterpriseRole;
import com.mysoft.permission.role.specifications.UniqueEnterpriseUserRoleSpecification;
import com.mysoft.permission.user.appservice.EnterpriseUserAppService;
import com.mysoft.permission.user.convert.EnterpriseUserRoleConvert;
import com.mysoft.permission.user.domainservice.EnterpriseUserDomainService;
import com.mysoft.permission.user.dto.CreateEnterpriseUserReqDTO;
import com.mysoft.permission.user.dto.EnterpriseUserPageReqDTO;
import com.mysoft.permission.user.dto.EnterpriseUserWithRoleResDTO;
import com.mysoft.permission.user.entity.User;
import com.mysoft.permission.user.model.UserWithRole;
import com.mysoft.permission.user.repository.EnterpriseUserRoleRepository;
import com.mysoft.permission.user.valueobject.EnterpriseUserRole;
import com.mysoft.rdcapiclient.RdcApiClient;
import com.mysoft.rdcapiclient.dto.RdcApiAddTeamMemberReqDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RequiredArgsConstructor
@Service
@Slf4j
public class EnterpriseUserAppServiceImpl implements EnterpriseUserAppService {

    final EnterpriseUserRoleRepository enterpriseUserRoleRepository;
    final HasKeyRepository<User, String> userRepository;
    final HasKeyRepository<EnterpriseRole, Long> roleRepository;
    final EnterpriseUserDomainService enterpriseUserDomainService;
    final EnterpriseUserRoleConvert enterpriseUserRoleConvert;
    final IdentityClient identityClient;
    final IdentityServerProps identityServerProps;
    final EmailClient emailClient;
    final PermissionEmailProps permissionEmailProps;
    final RdcApiClient rdcApiClient;
    final EnterpriseClient enterpriseClient;
    final CurrentUser currentUser;

    @Override
    public PageResDTO<EnterpriseUserWithRoleResDTO> pageEnterpriseUserWithRoles(Long enterpriseId, EnterpriseUserPageReqDTO input) {
        PageQuery pageQuery = new PageQuery(input.getPageIndex(), input.getPageSize());
        PageResult<UserWithRole> userWithRolePageResult = enterpriseUserRoleRepository.pageUserWithRole(
                enterpriseId,
                input.getRoleId(),
                input.getRoleName(),
                input.getKeyword(),
                pageQuery);
        return new PageResDTO<>(userWithRolePageResult.getTotalCount(), enterpriseUserRoleConvert.toResDTOList(userWithRolePageResult.getItems()));
    }

    @Override
    public EnterpriseUserWithRoleResDTO getEnterpriseUserWithRole(Long enterpriseId, String userId) {
        User user = userRepository.getById(userId);
        if (user == null) {
            throw new NotFoundErrorException("用户【{0}】不存在或已删除", userId);
        }
        EnterpriseUserRole enterpriseUserRole = enterpriseUserRoleRepository.getOne(
                new UniqueEnterpriseUserRoleSpecification(enterpriseId, userId).toExpression()
        );
        if (enterpriseUserRole == null) {
            throw new NotFoundErrorException("企业【{0}】中的用户【{1}】未分配角色", enterpriseId, userId);
        }
        EnterpriseRole role = roleRepository.getById(enterpriseUserRole.getRoleId());
        if (role == null) {
            throw new NotFoundErrorException("角色【{0}】不存在或已删除", enterpriseUserRole.getRoleId());
        }
        return enterpriseUserRoleConvert.toResDTO(user, role);
    }


    @Transactional
    @Override
    public EnterpriseUserWithRoleResDTO createEnterpriseUser(Long enterpriseId, CreateEnterpriseUserReqDTO input) {
        input.checkValid();
        User user;
        // 用户不存在则直接添加成员
        if (StringUtils.isNotBlank(input.getUserId())) {
            user = userRepository.getById(input.getUserId());
            if (user == null) {
                user = new User(
                        input.getUserId(),
                        input.getName(),
                        input.getUsername(),
                        input.getEmail(),
                        input.getPhone(),
                        input.getIsOuter(),
                        null);
                userRepository.insert(user);

                log.info("用户【{}】创建成功", user.getUsername());
            }
        } else {
            // 外部用户需先添加到认证服务
            String password = AuthUtils.encryptDES(AuthUtils.generateTempPassword(), identityServerProps.getDesKey());
            UserResDTO userRes = identityClient.getUserByUserName(input.getUsername());
            if (userRes != null && userRes.getIsOuter()) {
                throw new BadRequestException("用户【{0}】已存在", userRes.getUserName());
            }
            CreateUserResDTO createUserRes = identityClient.addUser(
                    new CreateUserReqDTO(
                            input.getName(),
                            input.getUsername(),
                            input.getEmail(),
                            input.getPhone(),
                            password));
            EnterpriseInfoResDTO enterpriseInfoRes = enterpriseClient.getEnterpriseById(enterpriseId);
            rdcApiClient.addTeamMember(new RdcApiAddTeamMemberReqDTO(
                    enterpriseId.intValue(),
                    enterpriseInfoRes.getCode(),
                    input.getUsername(),
                    input.getName(),
                    1,
                    currentUser.getUsername(),
                    new Date()));
            user = new User(
                    createUserRes.getId(),
                    input.getName(),
                    input.getUsername(),
                    input.getEmail(),
                    input.getPhone(),
                    input.getIsOuter(),
                    password);
            userRepository.insert(user);

            log.info("外部用户【{}】创建成功", user.getUsername());
        }
        // 分配用户到企业角色
        EnterpriseRole role = enterpriseUserDomainService.assignToRole(
                user,
                enterpriseId,
                input.getRoleId());

        log.info("用户【{}】分配角色【{}】成功", user.getUsername(), role.getName());
        return enterpriseUserRoleConvert.toResDTO(user, role);
    }

    @Override
    public EnterpriseUserWithRoleResDTO assignEnterpriseUserToRole(Long enterpriseId, String userId, Long roleId) {
        User user = userRepository.getById(userId);
        if (user == null) {
            throw new NotFoundErrorException("用户【{0}】不存在或已删除", userId);
        }
        // 分配用户到企业角色
        EnterpriseRole role = enterpriseUserDomainService.assignToRole(
                user,
                enterpriseId,
                roleId);
        return enterpriseUserRoleConvert.toResDTO(user, role);
    }

    @Override
    public void deleteEnterpriseUser(Long enterpriseId, String userId) {
        // 删除用户和角色关系
        LambdaQueryWrapper<EnterpriseUserRole> queryWrapper = new LambdaQueryWrapper<EnterpriseUserRole>()
                .eq(EnterpriseUserRole::getEnterpriseId, enterpriseId)
                .eq(EnterpriseUserRole::getUserId, userId);
        var enterpriseUserRoles = enterpriseUserRoleRepository.list(queryWrapper);
        for (EnterpriseUserRole enterpriseUserRole : enterpriseUserRoles) {
            enterpriseUserRoleRepository.deleteById(enterpriseUserRole);
        }
    }

}
