package com.mysoft.leistd.exception;

/**
 * 不支持媒体数据异常，默认错误码：41500
 */
public class UnsupportedMediaTypeException extends BusinessException {

    public UnsupportedMediaTypeException(String messagePattern, Object... arguments) {
        super("415", messagePattern, arguments);
    }

    public UnsupportedMediaTypeException(Throwable cause, String messagePattern, Object... arguments) {
        super("415", cause, messagePattern, arguments);
    }
}
