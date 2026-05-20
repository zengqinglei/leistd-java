package com.mysoft.leistd.dstblock.interceptor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLock {
    /**
     * 分布式锁标识，支持解析 spring el 格式：abc、#{param}、#{user.id}、abc_#{param}_#{user.id}
     * 默认：类名.方法名 --> UserService.getUser
     */
    String key() default "";

    /**
     * 最大等待时间(默认30秒)
     */
    int waitTime() default 30;

    /**
     * 时间单位：默认为秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
