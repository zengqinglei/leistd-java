package com.mysoft.leistd.response;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一输出结果
 */
@RestControllerAdvice
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 2)
public class ResponseWrapperBodyAdvice implements ResponseBodyAdvice<Object> {
    private final ResponseHandler responseHandler;

    @SuppressWarnings("@")
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (sra == null) {
            return false;
        }
        HttpServletRequest request = sra.getRequest();
        // 判断请求是否有包装标记
        Object isWrapperSuccessValue = request.getAttribute(ResponseInterceptor.IS_WRAPPER_SUCCESS_RES_KEY);
        if (isWrapperSuccessValue == null) {
            return false;
        }
        return (Boolean) isWrapperSuccessValue;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return responseHandler.handle(returnType.getClass(), body);
    }
}
