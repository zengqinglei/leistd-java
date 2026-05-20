package com.mysoft.permission.role.valueobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mysoft.leistd.domain.entities.auditing.DeletionAuditedHasKeyEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@TableName(value = "role_permission")
public class RolePermission extends DeletionAuditedHasKeyEntity<Long> {

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 权限id
     */
    private Long permissionId;

    public RolePermission(Long roleId, Long permissionId, String creatorId, String creatorName) {
        this.roleId = roleId;
        this.permissionId = permissionId;
        this.setCreatorId(creatorId);
        this.setCreatorName(creatorName);
    }
}
