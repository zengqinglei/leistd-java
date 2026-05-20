package com.mysoft.leistd.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

/**
 * 异常包装接口
 */
public interface ExceptionHandler {

    ResponseEntity<Object> handle(Throwable e, HttpServletRequest request) throws Throwable;

}
