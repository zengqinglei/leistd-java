package com.mysoft.permission.role.domainservice;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mysoft.leistd.domain.repositories.HasKeyRepository;
import com.mysoft.leistd.exception.NotFoundErrorException;
import com.mysoft.permission.permission.entity.Permission;
import com.mysoft.permission.role.entity.EnterpriseRole;
import com.mysoft.permission.role.valueobject.RolePermission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnterpriseRolePermissionDomainService {
    final HasKeyRepository<RolePermission, Long> rolePermissionRepository;
    final HasKeyRepository<Permission, Long> permissionRepository;

    public HashMap<EnterpriseRole, List<String>> initRoleAndPermissions(Long enterpriseId) {
        HashMap<EnterpriseRole, List<String>> initEnterpriseRolePermissions = new HashMap<>();
        initEnterpriseRolePermissions.put(
                new EnterpriseRole(enterpriseId, "企业管理员", false, false, "SYSTEM", "SYSTEM"),
                List.of("All"));
        initEnterpriseRolePermissions.put(
                new EnterpriseRole(enterpriseId, "总部业财", true, false, "SYSTEM", "SYSTEM"),
                List.of("GoodsManage_Goods_Create",
                        "GoodsManage_Goods_Edit",
                        "GoodsManage_Goods_Delete",
                        "GoodsManage_GoodsApp_Config",
                        "TenantManage_YcTenant_View",
                        "TenantManage_YcTenant_Create",
                        "TenantManage_YcTenant_CreateCustomTenant",
                        "TenantManage_YcTenant_OpenCustomTenantDesign",
                        "TenantManage_YcTenant_RunAuth",
                        "TenantManage_YcTenant_ViewLicense",
                        "TenantManage_YcTestTenant_View",
                        "TenantManage_YcTestTenant_Create",
                        "TenantManage_YcTestTenant_CreateCustomTenant",
                        "TenantManage_YcTestTenant_OpenCustomTenantDesign",
                        "TenantManage_YcTestTenant_RunAuth",
                        "TenantManage_YcTestTenant_ViewLicense",
                        "TenantManage_YcTestTenant_Remove"));
        initEnterpriseRolePermissions.put(
                new EnterpriseRole(enterpriseId, "运维人员", true, false, "SYSTEM", "SYSTEM"),
                List.of("EnvironmentManage_Environment_View",
                        "EnvironmentManage_Environment_Create",
                        "EnvironmentManage_Environment_Edit",
                        "EnvironmentManage_Environment_Delete",
                        "EnvironmentManage_EnvironmentVars_View",
                        "EnvironmentManage_EnvironmentVars_Create",
                        "EnvironmentManage_EnvironmentVars_Edit",
                        "EnvironmentManage_EnvironmentVars_Delete",
                        "EnvironmentManage_Environment_VarsConfig",
                        "EnvironmentManage_Environment_GenerateLicense",
                        "EnvironmentManage_Environment_ViewLicense",
                        "EnvironmentManage_CustomEnvironment_View",
                        "EnvironmentManage_CustomEnvironment_Create",
                        "EnvironmentManage_CustomEnvironment_Edit",
                        "EnvironmentManage_CustomEnvironment_Delete",
                        "TenantManage_YcTenant_View",
                        "TenantManage_YcTenant_Create",
                        "TenantManage_YcTenant_CreateCustomTenant",
                        "TenantManage_YcTenant_OpenCustomTenantDesign",
                        "TenantManage_YcTenant_RunAuth",
                        "TenantManage_YcTenant_ViewLicense",
                        "TenantManage_YcTestTenant_View",
                        "TenantManage_YcTestTenant_Create",
                        "TenantManage_YcTestTenant_CreateCustomTenant",
                        "TenantManage_YcTestTenant_OpenCustomTenantDesign",
                        "TenantManage_YcTestTenant_RunAuth",
                        "TenantManage_YcTestTenant_ViewLicense",
                        "TenantManage_YcTestTenant_Remove",
                        "TenantManage_TjTenant_View",
                        "TenantManage_TjTenant_CreateTestTenant",
                        "TenantManage_TjTenant_EditTestTenant",
                        "TenantManage_TjTenant_CreateCustomTenant",
                        "TenantManage_TjTenant_CreateAuth",
                        "TenantManage_TjTenant_OpenCustomTenantDesign",
                        "TenantManage_TjTenant_RunAuth",
                        "TenantManage_TjTenant_ViewLicense",
                        "TenantManage_TjTenant_Remove"));
        initEnterpriseRolePermissions.put(
                new EnterpriseRole(enterpriseId, "租户管理员", true, false, "SYSTEM", "SYSTEM"),
                List.of("TenantManage_YcTestTenant_View",
                        "TenantManage_YcTestTenant_Create",
                        "TenantManage_YcTestTenant_CreateCustomTenant",
                        "TenantManage_YcTestTenant_OpenCustomTenantDesign",
                        "TenantManage_YcTestTenant_RunAuth",
                        "TenantManage_YcTestTenant_ViewLicense",
                        "TenantManage_YcTestTenant_Remove",
                        "TenantManage_TjTenant_View",
                        "TenantManage_TjTenant_CreateTestTenant",
                        "TenantManage_TjTenant_EditTestTenant",
                        "TenantManage_TjTenant_CreateCustomTenant",
                        "TenantManage_TjTenant_CreateAuth",
                        "TenantManage_TjTenant_OpenCustomTenantDesign",
                        "TenantManage_TjTenant_RunAuth",
                        "TenantManage_TjTenant_ViewLicense",
                        "TenantManage_TjTenant_Remove"));
        initEnterpriseRolePermissions.put(
                new EnterpriseRole(enterpriseId, "运营人员", true, false, "SYSTEM", "SYSTEM"),
                List.of("TenantManage_YcTenant_View",
                        "TenantManage_YcTestTenant_View",
                        "TenantManage_TjTenant_View"));
        initEnterpriseRolePermissions.put(
                new EnterpriseRole(enterpriseId, "企业成员", true, false, "SYSTEM", "SYSTEM"),
                List.of("EnterpriseManage_Enterprise_View"));
        return initEnterpriseRolePermissions;
    }

    public List<Permission> addRolePermissions(EnterpriseRole role, List<String> permissionCodes, String creatorId, String creatorName) {
        // 先删除关系
        LambdaQueryWrapper<RolePermission> rolePermissionQueryWrapper = new LambdaQueryWrapper<RolePermission>()
                .eq(RolePermission::getRoleId, role.getId());
        rolePermissionRepository.delete(rolePermissionQueryWrapper);

        // 挨个添加权限
        List<Permission> addPermissions = new ArrayList<>();
        List<Permission> allPermissions = permissionRepository.list();
        for (String permissionCode : permissionCodes) {
            if (permissionCode.equals("All")) {
                List<RolePermission> rolePermissions = allPermissions.stream()
                        .map(permission -> new RolePermission(role.getId(), permission.getId(), creatorId, creatorName))
                        .toList();
                role.getRolePermissions().addAll(rolePermissions);
                addPermissions.addAll(allPermissions);
                break;
            } else {
                Permission permission = allPermissions.stream()
                        .filter(item -> item.getCode().equals(permissionCode))
                        .findAny()
                        .orElse(null);
                if (permission == null) {
                    throw new NotFoundErrorException("权限【{0}】不存在或已删除", permissionCode);
                }
                role.getRolePermissions().add(new RolePermission(role.getId(), permission.getId(), creatorId, creatorName));
                addPermissions.add(permission);
            }
        }
        rolePermissionRepository.insertBatch(role.getRolePermissions());
        return addPermissions;
    }
}
