package com.mysoft.permission.role.appservice;

import com.mysoft.permission.role.dto.EnterpriseRoleResDTO;

import java.util.List;

/**
 * 角色应用服务
 */
public interface EnterpriseRoleAppService {

    /**
     * 查询企业的角色集合
     *
     * @param enterpriseId 企业Id
     * @return 返回企业的角色集合
     */
    List<EnterpriseRoleResDTO> listEnterpriseRoles(Long enterpriseId);
}
