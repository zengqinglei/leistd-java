package com.mysoft.leistd.security;

import com.mysoft.leistd.tracing.CorrelationIdProvider;
import com.mysoft.leistd.tracing.httpclient.CorrelationIdHeaderRequestInterceptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

/**
 * 分布式锁单元测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpClientCorrelationIdHeaderTest {
    @Autowired
    private CorrelationIdProvider correlationIdProvider;

    /**
     * 测试请求客户端传递用户信息
     */
    @Test
    public void testAddCorrelationIdHeader() {
        String correlationId = correlationIdProvider.create();
        try (AutoCloseable ignore = correlationIdProvider.change(correlationId)) {
            RestTemplate restTemplate = getRestTemplate();
            List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
            interceptors.add(new CorrelationIdHeaderRequestInterceptor());
            ResponseEntity<String> response = restTemplate.getForEntity("https://www.baidu.com", String.class);
            Assert.assertTrue(response.getStatusCode().is2xxSuccessful());
        } catch (Exception ex) {
            Assert.fail("链路Id设置失败：" + ex.getMessage());
        }
    }

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        restTemplate.setRequestFactory(requestFactory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @SuppressWarnings("all")
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                // 不抛异常
            }
        });
        return restTemplate;
    }
}
