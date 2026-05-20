package com.mysoft.leistd.tracing.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 链路追踪注解（一般用于调度任务、异步任务等后台线程的方法方法）
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CorrelationId {
    /**
     * 不赋值默认随机生成
     */
    String value() default "";
}
