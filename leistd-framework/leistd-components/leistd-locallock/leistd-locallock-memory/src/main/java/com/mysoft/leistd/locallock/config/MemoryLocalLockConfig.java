package com.mysoft.leistd.locallock.config;

import com.mysoft.leistd.locallock.ILocalLock;
import com.mysoft.leistd.locallock.impl.MemoryLocalLockImpl;
import com.mysoft.leistd.locallock.props.LocalLockProps;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemoryLocalLockConfig {
    @ConditionalOnMissingBean
    @Bean
    public ILocalLock configLocalLock(LocalLockProps localLockProps) {
        return new MemoryLocalLockImpl(localLockProps);
    }
}
