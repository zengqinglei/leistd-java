package com.mysoft.leistd.security;

import com.mysoft.leistd.security.client.DemoClient;
import com.mysoft.leistd.tracing.CorrelationIdProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 分布式锁单元测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FeignCorrelationIdHeaderTest {
    @Autowired
    private CorrelationIdProvider correlationIdProvider;
    @Autowired
    private DemoClient demoClient;

    /**
     * 测试请求客户端传递链路Id
     */
    @Test
    public void testAddUserHeader() {
        String correlationId = correlationIdProvider.create();
        try (AutoCloseable ignore = correlationIdProvider.change(correlationId)) {
            ResponseEntity<String> response = demoClient.get();
            Assert.assertTrue(response.getStatusCode().is2xxSuccessful());
        } catch (Exception ex) {
            Assert.fail("链路Id设置失败：" + ex.getMessage());
        }
    }
}
