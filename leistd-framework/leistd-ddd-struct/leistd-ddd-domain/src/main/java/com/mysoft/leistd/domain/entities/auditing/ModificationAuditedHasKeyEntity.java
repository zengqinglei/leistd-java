package com.mysoft.leistd.domain.entities.auditing;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 修改信息审计基类
 *
 * @param <TPrimaryKey> 主键类型
 */
@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class ModificationAuditedHasKeyEntity<TPrimaryKey extends Serializable> extends CreationAuditedHasKeyEntity<TPrimaryKey> {

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
