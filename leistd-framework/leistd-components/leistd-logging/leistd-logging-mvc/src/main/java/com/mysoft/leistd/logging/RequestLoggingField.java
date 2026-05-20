package com.mysoft.leistd.logging;

/**
 * 请求记录日志字段
 */
public enum RequestLoggingField {
    /**
     * 请求属性字段
     */
    REQUEST_PROPERTIES,
    /**
     * 请求头字段
     */
    REQUEST_HEADERS,
    /**
     * 请求体字段
     */
    REQUEST_BODY,
    /**
     * 输出头字段
     */
    RESPONSE_PROPERTIES,
    /**
     * 响应体字段
     */
    RESPONSE_BODY
}
