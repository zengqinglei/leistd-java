package com.mysoft.leistd.security;

import com.mysoft.leistd.mdc.MdcProvider;
import com.mysoft.leistd.security.mdc.SecurityLogMdc;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomSetUserFilter extends OncePerRequestFilter {
    final MdcProvider mdcProvider;
    final SecurityLogMdc securityLogMdc;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        securityLogMdc.addClient(mdcProvider);
        securityLogMdc.addUser(mdcProvider);
        filterChain.doFilter(request, response);
    }
}
