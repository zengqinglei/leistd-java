package com.mysoft.leistd.mdc.filter;

import com.mysoft.leistd.mdc.MdcProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 核心过滤器，框架优先级最高
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class CoreFilter extends OncePerRequestFilter {
    private final MdcProvider mdcProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } finally {
            // 清理Mdc增加的资源
            mdcProvider.close();
        }
    }
}
