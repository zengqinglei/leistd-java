package com.mysoft.leistd.tracing;

import com.mysoft.leistd.context.ApplicationContextHelper;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignCorrelationIdInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 读取链路追踪Id
        CorrelationIdProvider correlationIdProvider = ApplicationContextHelper.getRequiredBean(CorrelationIdProvider.class);
        String correlationId = correlationIdProvider.get();

        // 读取配置
        CorrelationIdProps correlationIdProps = ApplicationContextHelper.getRequiredBean(CorrelationIdProps.class);
        for (String headerName : correlationIdProps.getHttpHeaderName()) {
            requestTemplate.header(headerName, correlationId);
        }
    }
}
