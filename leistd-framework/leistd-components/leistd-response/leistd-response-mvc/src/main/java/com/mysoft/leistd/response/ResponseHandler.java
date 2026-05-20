package com.mysoft.leistd.response;

/**
 * 结果包装接口
 */
public interface ResponseHandler {
    Object handle(Class returnType, Object data);
}
