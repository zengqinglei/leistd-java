package com.mysoft.leistd.tracing.impl;

import com.mysoft.leistd.tracing.CorrelationIdProvider;

import java.util.UUID;

/**
 * 默认追踪Id提供服务
 */
public class CorrelationIdProviderImpl implements CorrelationIdProvider {
    private final ThreadLocal<String> currentCorrelationId = new ThreadLocal<>();

    @Override
    public String create() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public String get() {
        return currentCorrelationId.get();
    }

    @Override
    public AutoCloseable change(String correlationId) {
        String parent = currentCorrelationId.get();
        currentCorrelationId.set(correlationId);
        return () -> currentCorrelationId.set(parent);
    }
}
