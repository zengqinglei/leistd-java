package com.mysoft.permission.user.appservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mysoft.leistd.domain.entities.page.PageQuery;
import com.mysoft.leistd.domain.entities.page.PageResult;
import com.mysoft.leistd.domain.repositories.HasKeyRepository;
import com.mysoft.leistd.dto.model.PageResDTO;
import com.mysoft.leistd.exception.BadRequestException;
import com.mysoft.leistd.exception.NotFoundErrorException;
import com.mysoft.permission.common.properties.IdentityServerProps;
import com.mysoft.permission.user.appservice.AdminUserAppService;
import com.mysoft.permission.user.convert.AdminUserConvert;
import com.mysoft.permission.user.dto.AdminUserPageReqDTO;
import com.mysoft.permission.user.dto.AdminUserResDTO;
import com.mysoft.permission.user.dto.CreateAdminUserReqDTO;
import com.mysoft.permission.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminUserAppServiceImpl implements AdminUserAppService {

    final HasKeyRepository<User, String> userRepository;
    final AdminUserConvert adminUserConvert;
    final IdentityServerProps identityServerProps;

    @Override
    public AdminUserResDTO createAdminUser(CreateAdminUserReqDTO input) {
        User user = userRepository.getById(input.getUserId());
        if (user != null) {
            if (user.getIsAdmin()) {
                throw new BadRequestException("请勿重复添加管理员!");
            }
            user.admin();
            userRepository.updateById(user);
        } else {
            user = new User(
                    input.getUserId(),
                    input.getName(),
                    input.getUsername(),
                    input.getEmail(),
                    input.getPhone(),
                    input.getIsOuter(),
                    null);
            user.admin();
            userRepository.insert(user);
        }
        return adminUserConvert.toResDTO(user);
    }

    @Override
    public void deleteAdminUser(String adminUserId) {
        User user = userRepository.getById(adminUserId);
        if (user == null || !user.getIsAdmin()) {
            throw new NotFoundErrorException("用户【{0}】已删除或已不是管理员", adminUserId);
        }
        user.normal();
        userRepository.updateById(user);
    }

    @Override
    public PageResDTO<AdminUserResDTO> pageAdminUsers(AdminUserPageReqDTO input) {
        // 组织查询条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getIsAdmin, true)
                .eq(input.getIsEnabled() != null, User::getIsEnabled, input.getIsEnabled())
                .and(StringUtils.isNotBlank(input.getKeyword()), x -> x
                        .like(User::getUsername, input.getKeyword())
                        .or()
                        .like(User::getEmail, input.getKeyword())
                        .or()
                        .like(User::getPhone, input.getKeyword())
                        .or()
                        .like(User::getName, input.getKeyword()));
        queryWrapper.orderByDesc(User::getCreatedTime);
        // 查询分页数据
        PageResult<User> userPage = userRepository.page(
                queryWrapper,
                new PageQuery(input.getPageIndex(), input.getPageSize()));
        List<AdminUserResDTO> adminUseRespDTOList = adminUserConvert.toResDTOList(userPage.getItems());
        return new PageResDTO<>(userPage.getTotalCount(), adminUseRespDTOList);
    }
}
