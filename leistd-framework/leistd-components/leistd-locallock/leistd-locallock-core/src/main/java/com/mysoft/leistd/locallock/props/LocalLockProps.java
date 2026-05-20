package com.mysoft.leistd.locallock.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("local-lock")
public class LocalLockProps {
    /**
     * 本地锁前缀
     */
    @Value("${spring.application.name:default}:locallock")
    private String prefix;
}
