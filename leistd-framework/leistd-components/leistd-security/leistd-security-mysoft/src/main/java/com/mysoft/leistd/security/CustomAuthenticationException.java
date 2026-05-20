package com.mysoft.leistd.security;

import com.mysoft.leistd.exception.ExceptionHandlerAdvice;
import com.mysoft.leistd.exception.ForbiddenException;
import com.mysoft.leistd.exception.UnauthorizedErrorException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomAuthenticationException extends ExceptionHandlerAdvice {

    @ExceptionHandler({AuthenticationException.class, InvalidBearerTokenException.class})
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) throws Throwable {
        return exceptionHandler(new UnauthorizedErrorException(ex, "未授权"), request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) throws Throwable {
        return exceptionHandler(new ForbiddenException(ex, "禁止访问"), request);
    }

}
