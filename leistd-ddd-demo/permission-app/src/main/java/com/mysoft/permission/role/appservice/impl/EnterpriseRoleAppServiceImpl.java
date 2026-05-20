package com.mysoft.permission.role.appservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mysoft.leistd.domain.repositories.HasKeyRepository;
import com.mysoft.permission.role.appservice.EnterpriseRoleAppService;
import com.mysoft.permission.role.convert.EnterpriseRoleConvert;
import com.mysoft.permission.role.domainservice.EnterpriseRolePermissionDomainService;
import com.mysoft.permission.role.dto.EnterpriseRoleResDTO;
import com.mysoft.permission.role.entity.EnterpriseRole;
import com.mysoft.permission.role.valueobject.RolePermission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EnterpriseRoleAppServiceImpl implements EnterpriseRoleAppService {

    final HasKeyRepository<EnterpriseRole, Long> enterpriseRoleRepository;
    final HasKeyRepository<RolePermission, Long> rolePermissionRepository;
    final EnterpriseRolePermissionDomainService enterpriseRolePermissionDomainService;
    final EnterpriseRoleConvert enterpriseRoleConvert;

    @Transactional
    @Override
    public List<EnterpriseRoleResDTO> listEnterpriseRoles(Long enterpriseId) {
        // 查询企业所拥有的角色
        List<EnterpriseRole> enterpriseRoles = enterpriseRoleRepository.list(
                new LambdaQueryWrapper<EnterpriseRole>().eq(EnterpriseRole::getEnterpriseId, enterpriseId));
        if (enterpriseRoles.isEmpty()) {
            // 初始化企业角色
            HashMap<EnterpriseRole, List<String>> initEnterpriseRolePermissions = enterpriseRolePermissionDomainService.initRoleAndPermissions(enterpriseId);
            enterpriseRoleRepository.insertBatch(initEnterpriseRolePermissions.keySet());

            // 初始化企业角色权限
            enterpriseRoles = initEnterpriseRolePermissions.keySet().stream().toList();
            for (EnterpriseRole enterpriseRole : enterpriseRoles) {
                List<String> initPermissionCodes = initEnterpriseRolePermissions.get(enterpriseRole);
                enterpriseRolePermissionDomainService.addRolePermissions(
                        enterpriseRole,
                        initPermissionCodes,
                        "SYSTEM",
                        "SYSTEM");
            }
        }
        return enterpriseRoleConvert.toResDTOList(enterpriseRoles);
    }
}
