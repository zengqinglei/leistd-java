package com.mysoft.leistd.tracing.config;

import com.mysoft.leistd.tracing.CorrelationIdProvider;
import com.mysoft.leistd.tracing.impl.CorrelationIdProviderImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CorrelationIdConfig {
    @ConditionalOnMissingBean
    @Bean
    public CorrelationIdProvider registerCorrelationIdProvider() {
        return new CorrelationIdProviderImpl();
    }
}
