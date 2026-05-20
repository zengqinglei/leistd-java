package com.mysoft.leistd.tracing;

import com.mysoft.leistd.tracing.service.JobDemoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 链路追踪单元测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CorrelationIdProviderTest {
    @Autowired
    private CorrelationIdProvider correlationIdProvider;
    @Autowired
    private JobDemoService jobDemoService;

    /**
     * 测试链路追钟Id
     */
    @Test
    public void testGetAnsSetCorrelationId() {
        Assert.assertNull(correlationIdProvider.get());

        String oldCorrelationId = correlationIdProvider.create();
        try (AutoCloseable ignored = correlationIdProvider.change(oldCorrelationId)) {
            String newCorrelationId = correlationIdProvider.get();
            Assert.assertEquals(oldCorrelationId, newCorrelationId);
        } catch (Exception e) {
            Assert.fail(String.format("读写链路追踪Id失败：%s", e));
        }
        Assert.assertNull(correlationIdProvider.get());
    }

    /**
     * 测试后台任务拦截自动增加链路追踪Id
     */
    @Test
    public void testCorrelationIdInterceptor() {
        jobDemoService.execute();
    }
}
