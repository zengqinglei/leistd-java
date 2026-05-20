package com.mysoft.leistd.exception;

import com.mysoft.leistd.context.ApplicationContextHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Controller异常处理，统一输出结果
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class ExceptionHandlerAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exceptionHandler(Exception e, HttpServletRequest request) throws Throwable {
        return ApplicationContextHelper.getRequiredBean(com.mysoft.leistd.exception.ExceptionHandler.class).handle(e, request);
    }
}
