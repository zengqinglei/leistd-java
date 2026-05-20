package com.mysoft.leistd.security;

import com.mysoft.leistd.security.impl.DefaultCurrentPrincipalAccessorImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {
    @ConditionalOnMissingBean
    @Bean
    public CurrentPrincipalAccessor registerDefaultPrincipalAccessor() {
        return new DefaultCurrentPrincipalAccessorImpl();
    }
}
