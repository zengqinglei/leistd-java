package com.mysoft.leistd.tracing;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Configuration
@ConfigurationProperties("leistd.correlation-id")
public class CorrelationIdProps {
    /**
     * 是否启用链路追踪Id
     */
    private boolean enable = false;

    /**
     * 链路最终Id（默认：X-Correlation-Id）
     */
    private Set<String> httpHeaderName = new HashSet<>(
            Collections.singletonList("X-Correlation-Id")
    );

    /**
     * 是否设置输出头
     */
    private boolean setResponseHeader = true;
}
