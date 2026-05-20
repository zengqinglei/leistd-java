package com.mysoft.permission.controller.user;

import com.mysoft.leistd.dto.model.PageResDTO;
import com.mysoft.permission.controller.common.BaseController;
import com.mysoft.permission.user.appservice.AdminUserAppService;
import com.mysoft.permission.user.dto.AdminUserPageReqDTO;
import com.mysoft.permission.user.dto.AdminUserResDTO;
import com.mysoft.permission.user.dto.CreateAdminUserReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统管理员Api
 */
@RestController
@RequiredArgsConstructor
public class AdminUserController extends BaseController {

    final AdminUserAppService adminUserAppService;

    /**
     * 创建管理员
     */
    @PostMapping("/admin-users")
    public AdminUserResDTO createAdminUser(@Validated @RequestBody CreateAdminUserReqDTO input) {
        return adminUserAppService.createAdminUser(input);
    }

    /**
     * 删除管理员
     */
    @DeleteMapping("/admin-users/{adminUserId}")
    public void deleteAdminUser(@PathVariable String adminUserId) {
        adminUserAppService.deleteAdminUser(adminUserId);
    }

    /**
     * 获取管理员列表
     */
    @GetMapping("/admin-users")
    public PageResDTO<AdminUserResDTO> pageUsers(AdminUserPageReqDTO input) {
        return adminUserAppService.pageAdminUsers(input);
    }
}
