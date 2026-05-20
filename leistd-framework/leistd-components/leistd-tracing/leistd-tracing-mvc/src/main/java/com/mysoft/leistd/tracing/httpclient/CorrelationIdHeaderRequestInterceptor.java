package com.mysoft.leistd.tracing.httpclient;

import com.mysoft.leistd.context.ApplicationContextHelper;
import com.mysoft.leistd.tracing.CorrelationIdProps;
import com.mysoft.leistd.tracing.CorrelationIdProvider;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class CorrelationIdHeaderRequestInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        // 读取链路追踪Id
        CorrelationIdProvider correlationIdProvider = ApplicationContextHelper.getRequiredBean(CorrelationIdProvider.class);
        String correlationId = correlationIdProvider.get();

        // 读取配置
        CorrelationIdProps correlationIdProps = ApplicationContextHelper.getRequiredBean(CorrelationIdProps.class);
        for (String headerName : correlationIdProps.getHttpHeaderName()) {
            request.getHeaders().set(headerName, correlationId);
        }
        return execution.execute(request, body);
    }
}
