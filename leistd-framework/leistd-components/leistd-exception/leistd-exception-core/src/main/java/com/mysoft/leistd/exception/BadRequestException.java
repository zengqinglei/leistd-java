package com.mysoft.leistd.exception;


/**
 * 普通异常，默认错误码：40000
 */
public class BadRequestException extends BusinessException {

    public BadRequestException(String messagePattern, Object... arguments) {
        super("400", messagePattern, arguments);
    }

    public BadRequestException(Throwable cause, String messagePattern, Object... arguments) {
        super("400", cause, messagePattern, arguments);
    }
}
