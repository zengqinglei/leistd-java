package com.mysoft.leistd.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

/**
 * 全局异常配置
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties("leistd.global-exception")
public class GlobalExceptionProps {
    /**
     * 是否启处理全局异常
     */
    private boolean enable = false;
    /**
     * 排除的URI（如：/api/health/**）
     */
    private Set<String> excludePatterns = new HashSet<>();

    /**
     * 是否显示详情，默认开发环境（spring.profiles.active: local|dev|develop|development）显示
     */
    private Boolean isShowDetails;
}
