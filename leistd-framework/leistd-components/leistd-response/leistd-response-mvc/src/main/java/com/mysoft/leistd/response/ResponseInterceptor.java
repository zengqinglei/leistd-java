package com.mysoft.leistd.response;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 输出包装拦截器：用于读取注解配置输出包装控制
 */
@Slf4j
public class ResponseInterceptor implements HandlerInterceptor {
    /**
     * 是否包装成功输出Key
     */
    public static final String IS_WRAPPER_SUCCESS_RES_KEY = "IS_WRAPPER_SUCCESS_RES";
    /**
     * 是否包装失败输出Key
     */
    public static final String IS_WRAPPER_FAILURE_RES_KEY = "IS_WRAPPER_FAILURE_RES";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {
            boolean wrapperSuccessRes = false;
            boolean wrapperFailureRes = false;
            ResponseWrapper annotation = handlerMethod.getMethodAnnotation(ResponseWrapper.class);
            if (annotation == null) {
                Class<?> clazz = handlerMethod.getBeanType();
                annotation = AnnotationUtils.findAnnotation(clazz, ResponseWrapper.class);
            }
            if (annotation != null) {
                wrapperSuccessRes = annotation.wrapperSuccessRes();
                wrapperFailureRes = annotation.wrapperFailureRes();
            }
            request.setAttribute(IS_WRAPPER_SUCCESS_RES_KEY, wrapperSuccessRes);
            request.setAttribute(IS_WRAPPER_FAILURE_RES_KEY, wrapperFailureRes);
        }
        return true;
    }
}
