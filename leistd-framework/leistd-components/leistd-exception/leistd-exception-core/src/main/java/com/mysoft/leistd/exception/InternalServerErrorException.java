package com.mysoft.leistd.exception;

/**
 * 未知异常，默认错误码：50000
 */
public class InternalServerErrorException extends BusinessException {

    public InternalServerErrorException(String messagePattern, Object... arguments) {
        super("500", messagePattern, arguments);
    }

    public InternalServerErrorException(Throwable cause, String messagePattern, Object... arguments) {
        super("500", cause, messagePattern, arguments);
    }
}
