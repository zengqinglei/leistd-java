package com.mysoft.permission.controller.permission;


import com.mysoft.permission.controller.common.BaseController;
import com.mysoft.permission.permission.appservice.PermissionAppService;
import com.mysoft.permission.permission.dto.PermissionResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 权限Api
 */
@RestController
@RequiredArgsConstructor
public class PermissionController extends BaseController {

    final PermissionAppService permissionAppService;

    /**
     * 查询所有的权限
     */
    @GetMapping("/permissions")
    public List<PermissionResDTO> listAllPermission() {
        return permissionAppService.listAllPermission();
    }

}
