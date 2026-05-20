package com.mysoft.permission.user.appservice;

import com.mysoft.leistd.dto.model.PageResDTO;
import com.mysoft.permission.user.dto.AdminUserPageReqDTO;
import com.mysoft.permission.user.dto.AdminUserResDTO;
import com.mysoft.permission.user.dto.CreateAdminUserReqDTO;

/**
 * 管理员服务
 */
public interface AdminUserAppService {

    /**
     * 添加管理员
     *
     * @param input 管理员信息
     */
    AdminUserResDTO createAdminUser(CreateAdminUserReqDTO input);

    /**
     * 删除管理员
     *
     * @param adminUserId 管理员Id
     */
    void deleteAdminUser(String adminUserId);

    /**
     * 分页查询管理员列表
     *
     * @param input 分页查询条件
     * @return 返回管理员分页列表
     */
    PageResDTO<AdminUserResDTO> pageAdminUsers(AdminUserPageReqDTO input);
}
