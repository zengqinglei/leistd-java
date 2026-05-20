package com.mysoft.leistd.response;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 输出包装注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseWrapper {

    /**
     * 是否包装成功输出
     *
     * @return 默认包装
     */
    boolean wrapperSuccessRes() default true;

    /**
     * 是否包装失败输出
     *
     * @return 默认包装
     */
    boolean wrapperFailureRes() default true;
}
