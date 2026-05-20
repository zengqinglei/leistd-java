package com.mysoft.leistd.domain.entities.auditing;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 删除信息审计基类
 */
@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class DeletionAuditedEntity extends ModificationAuditedEntity {

    /**
     * 是否删除
     */
    @TableLogic
    private boolean isDeleted;

    /**
     * 删除人用户名
     */
    @TableField(fill = FieldFill.UPDATE)
    private String deleterId;

    /**
     * 删除人姓名
     */
    @TableField(fill = FieldFill.UPDATE)
    private String deleterName;

    /**
     * 删除时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date deletedTime;
}
