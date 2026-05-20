package com.mysoft.leistd.dstblock.config;

import com.mysoft.leistd.dstblock.props.DistributedLockProps;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

@Configuration
public class SpringRedisDstbLockConfig {
    @ConditionalOnMissingBean
    @Bean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory, DistributedLockProps distributedLockProps) {
        return new RedisLockRegistry(redisConnectionFactory, distributedLockProps.getPrefix());
    }
}
