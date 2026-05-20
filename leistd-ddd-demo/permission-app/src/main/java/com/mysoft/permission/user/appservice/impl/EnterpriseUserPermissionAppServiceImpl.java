package com.mysoft.permission.user.appservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mysoft.leistd.domain.repositories.HasKeyRepository;
import com.mysoft.leistd.exception.NotFoundErrorException;
import com.mysoft.permission.permission.convert.PermissionConvert;
import com.mysoft.permission.permission.dto.PermissionResDTO;
import com.mysoft.permission.permission.entity.Permission;
import com.mysoft.permission.role.entity.EnterpriseRole;
import com.mysoft.permission.role.specifications.UniqueEnterpriseUserRoleSpecification;
import com.mysoft.permission.role.valueobject.RolePermission;
import com.mysoft.permission.user.appservice.EnterpriseUserPermissionAppService;
import com.mysoft.permission.user.convert.UserJoinEnterprisesConvert;
import com.mysoft.permission.user.dto.UserJoinEnterprisesResDTO;
import com.mysoft.permission.user.entity.User;
import com.mysoft.permission.user.repository.EnterpriseUserRoleRepository;
import com.mysoft.permission.user.valueobject.EnterpriseUserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EnterpriseUserPermissionAppServiceImpl implements EnterpriseUserPermissionAppService {

    final HasKeyRepository<User, String> userRepository;
    final EnterpriseUserRoleRepository enterpriseUserRoleRepository;
    final HasKeyRepository<RolePermission, Long> rolePermissionRepository;
    final HasKeyRepository<EnterpriseRole, Long> roleRepository;
    final HasKeyRepository<Permission, Long> permissionRepository;
    final UserJoinEnterprisesConvert userJoinEnterprisesConvert;
    final PermissionConvert permissionConvert;

    @Override
    public List<PermissionResDTO> listEnterpriseUserPermissions(Long enterpriseId, String userId) {
        // 获取角色信息
        EnterpriseUserRole enterpriseUserRole = enterpriseUserRoleRepository.getOne(
                new UniqueEnterpriseUserRoleSpecification(enterpriseId, userId).toExpression()
        );
        if (enterpriseUserRole == null) {
            throw new NotFoundErrorException("企业【{0}】中的用户【{1}】未分配角色", enterpriseId, userId);
        }

        // 获取权限信息
        List<RolePermission> rolePermissions = rolePermissionRepository.list(
                new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId, enterpriseUserRole.getRoleId())
        );
        List<Permission> permissions = new ArrayList<>();
        for (RolePermission rolePermission : rolePermissions) {
            Permission permission = permissionRepository.getById(rolePermission.getPermissionId());
            permissions.add(permission);
        }
        return permissionConvert.toResDTOList(permissions);
    }

    @Override
    public UserJoinEnterprisesResDTO listUserJoinEnterprises(String userId) {
        // 获取用户信息
        User user = userRepository.getById(userId);
        if (user == null) {
            throw new NotFoundErrorException("用户【{0}】不存在或已删除", userId);
        }

        // 获取用户可访问的企业
        List<EnterpriseUserRole> enterpriseUserRoles = enterpriseUserRoleRepository.list(
                new LambdaQueryWrapper<EnterpriseUserRole>().eq(EnterpriseUserRole::getUserId, userId)
        );
        return userJoinEnterprisesConvert.toResDTO(user, enterpriseUserRoles);
    }
}
