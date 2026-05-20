package com.mysoft.leistd.exception;

/**
 * 未找到异常，默认错误码：40400
 */
public class NotFoundErrorException extends BusinessException {

    public NotFoundErrorException(String messagePattern, Object... arguments) {
        super("404", messagePattern, arguments);
    }

    public NotFoundErrorException(Throwable cause, String messagePattern, Object... arguments) {
        super("404", cause, messagePattern, arguments);
    }
}
