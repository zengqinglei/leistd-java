package com.mysoft.permission.role.appservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mysoft.leistd.domain.repositories.HasKeyRepository;
import com.mysoft.leistd.exception.ForbiddenException;
import com.mysoft.leistd.exception.NotFoundErrorException;
import com.mysoft.permission.permission.convert.PermissionConvert;
import com.mysoft.permission.permission.dto.PermissionResDTO;
import com.mysoft.permission.permission.entity.Permission;
import com.mysoft.permission.role.appservice.RolePermissionAppService;
import com.mysoft.permission.role.domainservice.EnterpriseRolePermissionDomainService;
import com.mysoft.permission.role.dto.CreateRolePermissionReqDTO;
import com.mysoft.permission.role.entity.EnterpriseRole;
import com.mysoft.permission.role.valueobject.RolePermission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RolePermissionAppServiceImpl implements RolePermissionAppService {

    final HasKeyRepository<EnterpriseRole, Long> roleRepository;
    final HasKeyRepository<RolePermission, Long> rolePermissionRepository;
    final HasKeyRepository<Permission, Long> permissionRepository;
    final EnterpriseRolePermissionDomainService enterpriseRolePermissionDomainService;
    final PermissionConvert permissionConvert;

    @Override
    public List<PermissionResDTO> listRolePermissions(Long roleId) {
        List<RolePermission> rolePermissions = rolePermissionRepository.list(
                new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId, roleId));
        List<Permission> permissions = new ArrayList<>();
        for (RolePermission rolePermission : rolePermissions) {
            Permission permission = permissionRepository.getById(rolePermission.getPermissionId());
            permissions.add(permission);
        }
        return permissionConvert.toResDTOList(permissions);
    }

    @Transactional
    @Override
    public List<PermissionResDTO> createRolePermissions(Long roleId, CreateRolePermissionReqDTO input) {
        EnterpriseRole enterpriseRole = roleRepository.getById(roleId);
        if (enterpriseRole == null) {
            throw new NotFoundErrorException("角色【{0}】不存在或已删除", roleId);
        }
        if (!enterpriseRole.getIsAllowEditPermission()) {
            throw new ForbiddenException("角色【{0}】的权限不允许编辑", enterpriseRole.getName());
        }
        List<Permission> permissions = enterpriseRolePermissionDomainService.addRolePermissions(
                enterpriseRole,
                input.getPermissionCodes(),
                null,
                null);
        return permissionConvert.toResDTOList(permissions);
    }
}
