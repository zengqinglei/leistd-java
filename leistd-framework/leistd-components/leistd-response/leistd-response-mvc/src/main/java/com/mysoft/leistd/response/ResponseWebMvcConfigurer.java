package com.mysoft.leistd.response;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * Mvc拦截器配置：注册输出包装拦截器
 */
@Configuration
public class ResponseWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 解决 Controller 返回 String 类型的时报格式转换异常
        ArrayList<HttpMessageConverter<?>> stringHttpMessageConverters = new ArrayList<>();
        for (HttpMessageConverter<?> converter : converters) {
            if (converter.getClass().isAssignableFrom(StringHttpMessageConverter.class)) {
                stringHttpMessageConverters.add(converter);
            }
        }
        converters.removeAll(stringHttpMessageConverters);
        converters.addAll(stringHttpMessageConverters);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ResponseInterceptor());
    }

    @ConditionalOnMissingBean
    @Bean
    public ResponseHandler registerResponseHandler() {
        return new DefaultResponseHandler();
    }
}
