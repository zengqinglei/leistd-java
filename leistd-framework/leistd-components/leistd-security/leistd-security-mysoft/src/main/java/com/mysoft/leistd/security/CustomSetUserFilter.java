package com.mysoft.leistd.security;

import com.mysoft.leistd.mdc.MdcProvider;
import com.mysoft.leistd.security.mdc.SecurityLogMdc;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
