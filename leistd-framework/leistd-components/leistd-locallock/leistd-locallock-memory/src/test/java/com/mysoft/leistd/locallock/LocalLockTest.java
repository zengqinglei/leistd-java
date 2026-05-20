package com.mysoft.leistd.locallock;

import com.mysoft.leistd.locallock.entity.User;
import com.mysoft.leistd.locallock.service.JobDemoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 分布式锁单元测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalLockTest {
    private static final Logger log = LoggerFactory.getLogger(LocalLockTest.class);
    @Autowired
    private ILocalLock localLock;
    @Autowired
    private JobDemoService jobDemoService;

    /**
     * 测试锁
     */
    @Test
    public void testLock() {
        try (AutoCloseable ignore = localLock.lock("test-lock")) {
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
        int numberOfThreads = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        CompletableFuture<?>[] futures = IntStream.range(0, numberOfThreads)
                .mapToObj(taskId -> CompletableFuture.runAsync(() -> {
                    try (AutoCloseable ignore = localLock.lock("test-lock", 10, TimeUnit.SECONDS)) {
                       Assert.assertTrue("未出现异常", true);
                       log.info("【{}】任务{}执行", new Date(), taskId);
                       Thread.sleep(1000);
                    } catch (Exception e) {
                        Assert.fail(e.getMessage());
                    }
                }, executorService))
                .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(futures)
                .thenRun(() -> {
                    System.out.println("All tasks completed");
                })
                .join(); // 等待所有任务完成

        executorService.shutdown(); // 关闭线程池
    }

    /**
     * 测试方法拦截自动加锁
     */
    @Test
    public void testAopLock() {
        jobDemoService.execute(10L, new User("zengql"));
    }
}
