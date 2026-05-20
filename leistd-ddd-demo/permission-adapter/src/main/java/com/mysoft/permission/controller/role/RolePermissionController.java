package com.mysoft.permission.controller.role;

import com.mysoft.permission.controller.common.BaseController;
import com.mysoft.permission.permission.dto.PermissionResDTO;
import com.mysoft.permission.role.appservice.RolePermissionAppService;
import com.mysoft.permission.role.dto.CreateRolePermissionReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色权限Api
 */
@RestController
@RequiredArgsConstructor
public class RolePermissionController extends BaseController {

    final RolePermissionAppService rolePermissionAppService;

    /**
     * 查询角色下面的权限列表
     */
    @GetMapping("/roles/{roleId}/permissions")
    public List<PermissionResDTO> listRolePermissions(@PathVariable Long roleId) {
        return rolePermissionAppService.listRolePermissions(roleId);
    }

    /**
     * 新增角色拥有的权限
     */
    @PostMapping("/roles/{roleId}/permissions")
    public List<PermissionResDTO> createRolePermissions(@PathVariable Long roleId, @Validated @RequestBody CreateRolePermissionReqDTO input) {
        return rolePermissionAppService.createRolePermissions(roleId, input);
    }

}
