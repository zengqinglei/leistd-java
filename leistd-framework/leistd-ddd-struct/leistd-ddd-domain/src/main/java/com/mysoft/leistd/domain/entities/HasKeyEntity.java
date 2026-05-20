package com.mysoft.leistd.domain.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 含有主键的实体
 *
 * @param <TPrimaryKey> 泛型类型
 */
@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class HasKeyEntity<TPrimaryKey extends Serializable> extends Entity {
    /**
     * 主键Id
     */
    private TPrimaryKey id;

    /**
     * 获取主键集合
     *
     * @return 返回主键集合
     */
    @Override
    public Object[] getKeys() {
        return new Object[]{id};
    }
}
