package com.mysoft.permission.user.repository;

import com.mysoft.leistd.domain.entities.page.PageQuery;
import com.mysoft.leistd.domain.entities.page.PageResult;
import com.mysoft.leistd.domain.repositories.HasKeyRepository;
import com.mysoft.permission.user.model.UserWithRole;
import com.mysoft.permission.user.valueobject.EnterpriseUserRole;

public interface EnterpriseUserRoleRepository extends HasKeyRepository<EnterpriseUserRole, Long> {
    /**
     * 查询用户角色列表
     */
    PageResult<UserWithRole> pageUserWithRole(Long enterpriseId, Long roleId, String roleName, String keyword, PageQuery pageQuery);

}

