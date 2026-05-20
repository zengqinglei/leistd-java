package com.mysoft.leistd.logging;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
@ConfigurationProperties("leistd.request-logging")
public class RequestLoggingProps {
    /**
     * 是否启用
     */
    @Getter
    @Setter
    private boolean enable = false;

    /**
     * 请求记录的字段
     */
    @Getter
    @Setter
    private Set<RequestLoggingField> loggingFields = new HashSet<>(
            Arrays.asList(
                    RequestLoggingField.REQUEST_PROPERTIES,
                    RequestLoggingField.REQUEST_HEADERS,
                    RequestLoggingField.RESPONSE_PROPERTIES
            )
    );

    /**
     * 包含的URI（默认：/**）
     */
    @Getter
    @Setter
    private Set<String> includePatterns = new HashSet<>(
            Collections.singletonList(
                    "/**"
            )
    );

    /**
     * 排除的URI（如：/api/health/**）
     */
    @Getter
    @Setter
    private Set<String> excludePatterns = new HashSet<>();

    /**
     * 排除Header（如：Authorization）
     */
    @Getter
    @Setter
    private Set<String> excludeHeaders = new HashSet<>();

    /**
     * 排除Body的URI（如：/api/health/**）
     */
    @Getter
    @Setter
    private Set<String> excludeBodyPatterns = new HashSet<>();

    /**
     * 要记录的最大请求主体大小（以字节为单位）,默认为 32 KB
     */
    @Getter
    @Setter
    private int requestBodyLimit = 32 * 1024;

    /**
     * 要记录的最大响应主体大小（以字节为单位）,默认为 32 KB
     */
    @Getter
    @Setter
    private int responseBodyLimit = 32 * 1024;

    private final PathMatcher matcher = new AntPathMatcher();

    public boolean isEnabled(String requestURI) {
        if (!this.enable || this.loggingFields == null || this.loggingFields.isEmpty()) {
            return false;
        }
        if (includePatterns.stream().noneMatch(includePattern -> matcher.match(includePattern, requestURI))) {
            return false;
        }
        if (excludePatterns == null) {
            return true;
        }
        return excludePatterns.stream().noneMatch(excludePattern -> matcher.match(excludePattern, requestURI));
    }

    public boolean isLogRequestBody(String requestURI) {
        if (!this.loggingFields.contains(RequestLoggingField.REQUEST_BODY)) {
            return false;
        }
        if (this.excludeBodyPatterns == null) {
            return true;
        }
        return this.excludeBodyPatterns.stream().noneMatch(excludePattern -> matcher.match(excludePattern, requestURI));
    }

    public boolean isLogResponseBody(String requestURI) {
        if (!this.loggingFields.contains(RequestLoggingField.RESPONSE_BODY)) {
            return false;
        }
        if (this.excludeBodyPatterns == null) {
            return true;
        }
        return this.excludeBodyPatterns.stream().noneMatch(excludePattern -> matcher.match(excludePattern, requestURI));
    }
}
