package com.mysoft.permission.role.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mysoft.leistd.domain.entities.auditing.DeletionAuditedHasKeyEntity;
import com.mysoft.permission.role.valueobject.RolePermission;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@TableName(value = "role")
public class EnterpriseRole extends DeletionAuditedHasKeyEntity<Long> {

    /**
     * 企业id
     */
    private Long enterpriseId;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 是否允许编辑权限
     */
    private Boolean isAllowEditPermission;

    /**
     * 是否允许删除
     */
    private Boolean isAllowDelete;

    /**
     * 拥有的权限
     */
    @TableField(exist = false)
    private List<RolePermission> rolePermissions = new ArrayList<>();

    public EnterpriseRole(Long enterpriseId, String name,Boolean isAllowEditPermission,Boolean isAllowDelete, String creatorId, String creatorName) {
        this.enterpriseId = enterpriseId;
        this.name = name;
        this.isAllowEditPermission = isAllowEditPermission;
        this.isAllowDelete = isAllowDelete;
        this.setCreatorId(creatorId);
        this.setCreatorName(creatorName);
    }

}
