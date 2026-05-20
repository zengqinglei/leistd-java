package com.mysoft.leistd.tracing.filter;

import com.mysoft.leistd.mdc.MdcProvider;
import com.mysoft.leistd.tracing.CorrelationIdProps;
import com.mysoft.leistd.tracing.CorrelationIdProvider;
import com.mysoft.leistd.tracing.mdc.CorrelationIdLogMdc;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE + 1)
public class CorrelationIdFilter extends OncePerRequestFilter {
    final MdcProvider mdcProvider;
    final CorrelationIdProps correlationIdProps;
    final CorrelationIdProvider correlationIdProvider;
    final CorrelationIdLogMdc correlationIdLogMdc;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!correlationIdProps.isEnable()) {
            filterChain.doFilter(request, response);
            return;
        }

        String correlationId = getCorrelationIdFromRequest(request);
        try (AutoCloseable ignored = correlationIdProvider.change(correlationId)) {
            correlationIdLogMdc.add(mdcProvider);
            filterChain.doFilter(request, response);
        } finally {
            checkAndSetCorrelationIdOnResponse(response, correlationId);
        }
    }

    private String getCorrelationIdFromRequest(HttpServletRequest request) {
        String correlationId = null;
        for (String headerName : correlationIdProps.getHttpHeaderName()) {
            correlationId = request.getHeader(headerName);
            if (StringUtils.isBlank(correlationId)) {
                correlationId = (String) request.getAttribute(headerName);
            }
            if (StringUtils.isBlank(correlationId)) {
                correlationId = correlationIdProvider.create();
                request.setAttribute(headerName, correlationId);
            }
            if (StringUtils.isNotBlank(correlationId)) {
                break;
            }
        }
        return correlationId;
    }

    private void checkAndSetCorrelationIdOnResponse(HttpServletResponse response, String correlationId) {
        if (response.isCommitted()) {
            return;
        }

        if (!correlationIdProps.isSetResponseHeader()) {
            return;
        }

        for (String headerName : correlationIdProps.getHttpHeaderName()) {
            if (response.containsHeader(headerName)) {
                return;
            }
            response.addHeader(headerName, correlationId);
        }
    }
}
