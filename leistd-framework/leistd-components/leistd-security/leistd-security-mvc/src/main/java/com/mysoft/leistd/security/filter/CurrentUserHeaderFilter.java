package com.mysoft.leistd.security.filter;

import com.mysoft.leistd.mdc.MdcProvider;
import com.mysoft.leistd.security.ClaimTypes;
import com.mysoft.leistd.security.ClaimsPrincipal;
import com.mysoft.leistd.security.CurrentPrincipalAccessor;
import com.mysoft.leistd.security.CurrentUserHeaderProps;
import com.mysoft.leistd.security.mdc.SecurityLogMdc;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE + 2)
@RequiredArgsConstructor
public class CurrentUserHeaderFilter extends OncePerRequestFilter {
    final CurrentUserHeaderProps currentUserHeaderProps;
    final CurrentPrincipalAccessor currentPrincipalAccessor;
    final MdcProvider mdcProvider;
    final SecurityLogMdc securityLogMdc;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!currentUserHeaderProps.isEnable()) {
            filterChain.doFilter(request, response);
            return;
        }
        String userId = getHeaderValue(request, currentUserHeaderProps.getUserId());
        String username = getHeaderValue(request, currentUserHeaderProps.getUsername());
        // 用户Id或用户名均未提供则跳过
        if (StringUtils.isBlank(userId) && StringUtils.isBlank(username)) {
            filterChain.doFilter(request, response);
            return;
        }
        String name = getHeaderValue(request, currentUserHeaderProps.getName());
        String phoneNumber = getHeaderValue(request, currentUserHeaderProps.getPhoneNumber());
        String email = getHeaderValue(request, currentUserHeaderProps.getEmail());
        Map<String, Object> claims = new HashMap<>();
        claims.put(ClaimTypes.UserId, userId);
        claims.put(ClaimTypes.UserName, username);
        claims.put(ClaimTypes.Name, name);
        claims.put(ClaimTypes.PhoneNumber, phoneNumber);
        claims.put(ClaimTypes.Email, email);
        ClaimsPrincipal currentPrincipal = new ClaimsPrincipal(claims);
        try (AutoCloseable ignore = currentPrincipalAccessor.change(currentPrincipal)) {
            securityLogMdc.addUser(mdcProvider);
            filterChain.doFilter(request, response);
        }
    }

    @SneakyThrows
    private String getHeaderValue(HttpServletRequest request, String headerName) {
        String value = request.getHeader(headerName);
        if (StringUtils.isBlank(value)) {
            return value;
        } else {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.name());
        }
    }
}
