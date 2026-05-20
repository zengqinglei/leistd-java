package com.mysoft.leistd.exception;

import com.mysoft.leistd.runtime.EnvRuntime;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalExceptionHandlerConfig {
    @ConditionalOnMissingBean
    @Bean
    public ExceptionHandler registerExceptionHandler(GlobalExceptionProps props, EnvRuntime envRuntime) {
        return new DefaultExceptionHandler(props, envRuntime);
    }
}
