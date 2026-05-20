package com.mysoft.leistd.tracing.service;

import com.mysoft.leistd.tracing.CorrelationIdProvider;
import com.mysoft.leistd.tracing.interceptor.CorrelationId;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobDemoService {
    final CorrelationIdProvider correlationIdProvider;

    @CorrelationId
    public void execute() {
        Assert.assertNotNull(correlationIdProvider.get());
    }
}
