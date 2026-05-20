package com.mysoft.leistd.mdc.config;

import com.mysoft.leistd.mdc.MdcProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MdcConfig {
    @ConditionalOnMissingBean
    @Bean
    public MdcProvider registerMdcProvider() {
        return new MdcProvider();
    }
}
