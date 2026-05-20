package com.mysoft.leistd.tracing;

/**
 * 追踪Id提供服务
 */
public interface CorrelationIdProvider {
    /**
     * 生成追踪Id
     *
     * @return 返回追踪Id
     */
    String create();

    /**
     * 获取追踪Id
     *
     * @return 返回追踪Id
     */
    String get();

    /**
     * 变更追踪Id
     *
     * @param correlationId 追踪Id
     */
    AutoCloseable change(String correlationId);
}
