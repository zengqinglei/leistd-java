package com.mysoft.leistd.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * 全局异常过滤器
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE + 4)
public class GlobalExceptionFilter extends OncePerRequestFilter {
    /**
     * 是否包装失败输出Key
     */
    public static final String IS_WRAPPER_FAILURE_RES_KEY = "IS_WRAPPER_FAILURE_RES";

    final GlobalExceptionProps globalExceptionProps;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 判断请求是否有包装标记
        boolean isWrapperFailureValue = true;
        Set<String> excludePatterns = globalExceptionProps.getExcludePatterns();
        PathMatcher matcher = new AntPathMatcher();
        if (!globalExceptionProps.isEnable() || (excludePatterns != null && excludePatterns.stream().anyMatch(excludePattern -> matcher.match(excludePattern, request.getRequestURI())))) {
            isWrapperFailureValue = false;
        }
        request.setAttribute(IS_WRAPPER_FAILURE_RES_KEY, isWrapperFailureValue);
        filterChain.doFilter(request, response);
    }
}
