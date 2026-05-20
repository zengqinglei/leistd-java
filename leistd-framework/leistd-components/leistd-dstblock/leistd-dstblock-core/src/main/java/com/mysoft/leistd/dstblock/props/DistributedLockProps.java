package com.mysoft.leistd.dstblock.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("distributed-lock")
public class DistributedLockProps {
    /**
     * 分布式锁前缀
     */
    @Value("${spring.application.name:default}:dstblock")
    private String prefix;
}
