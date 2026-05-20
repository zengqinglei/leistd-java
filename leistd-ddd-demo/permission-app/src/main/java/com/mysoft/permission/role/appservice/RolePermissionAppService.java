package com.mysoft.permission.role.appservice;

import com.mysoft.permission.permission.dto.PermissionResDTO;
import com.mysoft.permission.role.dto.CreateRolePermissionReqDTO;

import java.util.List;

/**
 * 角色权限应用服务
 */
public interface RolePermissionAppService {

    /**
     * 查询角色拥有的权限集合
     *
     * @param roleId 角色Id
     * @return 返回角色拥有的权限集合
     */
    List<PermissionResDTO> listRolePermissions(Long roleId);

    /**
     * 新增角色权限
     *
     * @param input 角色权限信息
     * @return 返回角色拥有的权限集合
     */
    List<PermissionResDTO> createRolePermissions(Long roleId, CreateRolePermissionReqDTO input);
}
