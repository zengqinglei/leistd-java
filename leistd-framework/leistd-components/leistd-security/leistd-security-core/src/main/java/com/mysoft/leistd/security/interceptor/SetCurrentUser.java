package com.mysoft.leistd.security.interceptor;

import java.lang.annotation.*;

/**
 * 设置当前用户信息（字段支持解析 spring el 格式：abc、#{param}、#{user.id}、abc_#{param}_#{user.Username}）
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SetCurrentUser {
    /**
     * 用户Id（默认：#{userId}）
     */
    String userId() default "#{userId}";

    /**
     * 用户名（默认：#{username}）
     */
    String username() default "#{username}";

    /**
     * 用户姓名（默认：#{name}）
     */
    String name() default "#{name}";

    /**
     * 电话号码（默认：#{phoneNumber}）
     */
    String phoneNumber() default "#{phoneNumber}";

    /**
     * 邮箱（默认：#{email}）
     */
    String email() default "#{email}";
}
