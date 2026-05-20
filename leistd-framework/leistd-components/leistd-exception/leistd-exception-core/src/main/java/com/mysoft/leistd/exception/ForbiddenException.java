package com.mysoft.leistd.exception;

/**
 * 禁止访问异常，默认错误码：40300
 */
public class ForbiddenException extends BusinessException {

    public ForbiddenException(String messagePattern, Object... arguments) {
        super("403", messagePattern, arguments);
    }

    public ForbiddenException(Throwable cause, String messagePattern, Object... arguments) {
        super("403", cause, messagePattern, arguments);
    }
}
