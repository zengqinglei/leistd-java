package com.mysoft.leistd.domain.entities.auditing;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.mysoft.leistd.domain.entities.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 创建信息审计类
 */
@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class CreationAuditedEntity extends Entity {
    /**
     * 创建人用户名
     */
    @TableField(fill = FieldFill.INSERT)
    private String creatorId;

    /**
     * 创建人姓名
     */
    @TableField(fill = FieldFill.INSERT)
    private String creatorName;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;
}
