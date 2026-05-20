package com.mysoft.leistd.domain.entities.auditing;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 修改信息审计实体类
 */
@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class ModificationAuditedEntity extends CreationAuditedEntity {
    /**
     * 修改人用户名
     */
    @TableField(fill = FieldFill.UPDATE)
    private String lastModifierId;

    /**
     * 修改人姓名
     */
    @TableField(fill = FieldFill.UPDATE)
    private String lastModifierName;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date lastModifiedTime;
}
