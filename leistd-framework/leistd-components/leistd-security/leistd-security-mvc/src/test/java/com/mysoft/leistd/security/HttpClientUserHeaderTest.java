package com.mysoft.leistd.security;

import com.mysoft.leistd.security.httpclient.UserHeaderRequestInterceptor;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分布式锁单元测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpClientUserHeaderTest {
    @Autowired
    private CurrentPrincipalAccessor currentPrincipalAccessor;

    /**
     * 测试请求客户端传递用户信息
     */
    @Test
    public void testAddUserHeader() {
        String username = "zhangs";
        Map<String, Object> claims = new HashMap<>();
        claims.put(ClaimTypes.UserId, "4a2c0e8b-019b-419a-b730-96e37334783b");
        claims.put(ClaimTypes.UserName, username);
        claims.put(ClaimTypes.Name, "张三");
        claims.put(ClaimTypes.PhoneNumber, "15827108888");
        claims.put(ClaimTypes.Email, "15827108888@qq.com");
        ClaimsPrincipal currentPrincipal = new ClaimsPrincipal(claims);
        try (AutoCloseable ignore = currentPrincipalAccessor.change(currentPrincipal)) {
            RestTemplate restTemplate = getRestTemplate();
            List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
            interceptors.add(new UserHeaderRequestInterceptor());
            ResponseEntity<String> response = restTemplate.getForEntity("https://www.baidu.com", String.class);
            Assert.assertTrue(response.getStatusCode().is2xxSuccessful());
        } catch (Exception ex) {
            Assert.fail("用户信息设置失败：" + ex.getMessage());
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
