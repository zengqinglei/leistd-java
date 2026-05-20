package com.mysoft.leistd.dstblock;

import com.mysoft.leistd.dstblock.entity.User;
import com.mysoft.leistd.dstblock.service.JobDemoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁单元测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DistributedLockTest {
    @Autowired
    private IDistributedLock distributedLock;
    @Autowired
    private JobDemoService jobDemoService;

    /**
     * 测试锁
     */
    @Test
    public void testLock() {
        try (AutoCloseable ignore = distributedLock.lock("test-lock")) {
            Assert.assertTrue("未出现异常", true);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * 测试锁并自动解锁
     */
    @Test
    public void testLockAndAutoUnlock() {
        try (AutoCloseable ignore = distributedLock.lock("test-lock", 10, TimeUnit.SECONDS)) {
            Assert.assertTrue("未出现异常", true);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * 测试方法拦截自动加锁
     */
    @Test
    public void testAopLock() {
        jobDemoService.execute(10L, new User("zengql"));
    }
}
