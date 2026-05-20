package com.mysoft.permission.permission.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mysoft.leistd.domain.entities.auditing.DeletionAuditedHasKeyEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@TableName(value = "permission")
public class Permission extends DeletionAuditedHasKeyEntity<Long> {

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限显示名称
     */
    private String displayName;

    /**
     * 权限编码
     */
    private String code;

}
