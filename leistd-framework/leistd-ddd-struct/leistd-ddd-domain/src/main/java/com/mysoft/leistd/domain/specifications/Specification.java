package com.mysoft.leistd.domain.specifications;

import com.baomidou.mybatisplus.core.conditions.Wrapper;

/**
 * 规约接口
 *
 * @param <T> 泛型对象
 */
public interface Specification<T> {
    /**
     * 转规约表达式
     *
     * @return 表达式（mybatis-plus wrapper）
     */
    public abstract Wrapper<T> toExpression();
}
