package com.mysoft.leistd.exception;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常包装接口
 */
public interface ExceptionHandler {

    ResponseEntity<Object> handle(Throwable e, HttpServletRequest request) throws Throwable;

}
