package com.mysoft.leistd.tracing.interceptor;

import com.mysoft.leistd.mdc.MdcProvider;
import com.mysoft.leistd.tracing.CorrelationIdProvider;
import com.mysoft.leistd.tracing.mdc.CorrelationIdLogMdc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 链路追踪拦截器（一般用于调度任务、异步任务等后台线程的方法方法）
 */
@Component
@Aspect
@Slf4j
@Order(0)
@RequiredArgsConstructor
public class CorrelationIdAspect {
    final CorrelationIdProvider correlationIdProvider;
    final CorrelationIdLogMdc correlationIdLogMdc;

    @Around(value = "@annotation(correlationId)")
    public Object doAround(ProceedingJoinPoint joinPoint, CorrelationId correlationId) throws Throwable {
        String traceId = correlationId.value();
        if (StringUtils.isBlank(correlationId.value())) {
            traceId = correlationIdProvider.create();
        }
        try (AutoCloseable ignored = correlationIdProvider.change(traceId); MdcProvider mdcProvider = new MdcProvider()) {
            correlationIdLogMdc.add(mdcProvider);
            return joinPoint.proceed();
        }
    }
}
