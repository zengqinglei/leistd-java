package com.mysoft.leistd.exception;

/**
 * 未授权异常，默认错误码：40100
 */
public class UnauthorizedErrorException extends BusinessException {

    public UnauthorizedErrorException(String messagePattern, Object... arguments) {
        super("401", messagePattern, arguments);
    }

    public UnauthorizedErrorException(Throwable cause, String messagePattern, Object... arguments) {
        super("401", cause, messagePattern, arguments);
    }
}
