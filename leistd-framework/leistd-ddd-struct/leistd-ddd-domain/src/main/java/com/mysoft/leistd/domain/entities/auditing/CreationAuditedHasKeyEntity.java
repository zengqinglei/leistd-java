package com.mysoft.leistd.domain.entities.auditing;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.mysoft.leistd.domain.entities.HasKeyEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建信息审计基类
 *
 * @param <TPrimaryKey> 主键类型
 */
@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class CreationAuditedHasKeyEntity<TPrimaryKey extends Serializable> extends HasKeyEntity<TPrimaryKey> {

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
