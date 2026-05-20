package com.mysoft.permission.user.valueobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mysoft.leistd.domain.entities.auditing.DeletionAuditedHasKeyEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@TableName(value = "enterprise_user_role")
public class EnterpriseUserRole extends DeletionAuditedHasKeyEntity<Long> {

    /**
     * 企业id
     */
    private Long enterpriseId;

    /**
     * 企业用户id
     */
    private String userId;

    /**
     * 角色id
     */
    private Long roleId;

    public EnterpriseUserRole(Long enterpriseId, String userId, Long roleId) {
        this.enterpriseId = enterpriseId;
        this.userId = userId;
        this.roleId = roleId;
    }
}
