package com.mysoft.permission.role.specifications;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mysoft.leistd.domain.specifications.Specification;
import com.mysoft.permission.user.valueobject.EnterpriseUserRole;

/**
 * 根据企业Id、用户Id查询唯一用户角色关系
 */
public class UniqueEnterpriseUserRoleSpecification implements Specification<EnterpriseUserRole> {
    final Long enterpriseId;
    final String userId;

    public UniqueEnterpriseUserRoleSpecification(Long enterpriseId, String userId) {
        this.enterpriseId = enterpriseId;
        this.userId = userId;
    }

    @Override
    public Wrapper<EnterpriseUserRole> toExpression() {
        return new LambdaQueryWrapper<EnterpriseUserRole>()
                .eq(EnterpriseUserRole::getEnterpriseId, enterpriseId)
                .eq(EnterpriseUserRole::getUserId, userId);
    }
}
