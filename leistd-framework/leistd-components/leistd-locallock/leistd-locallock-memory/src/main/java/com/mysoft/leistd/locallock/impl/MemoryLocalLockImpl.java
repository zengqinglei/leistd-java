package com.mysoft.leistd.locallock.impl;

import com.mysoft.leistd.locallock.ILocalLock;
import com.mysoft.leistd.locallock.ILock;
import com.mysoft.leistd.exception.CommonException;
import com.mysoft.leistd.locallock.props.LocalLockProps;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.concurrent.*;

/**
 * 默认本地锁服务（内存）实现
 */
@Slf4j
@RequiredArgsConstructor
public class MemoryLocalLockImpl implements ILocalLock {
    final LocalLockProps localLockProps;
    final ConcurrentMap<String, SemaphoreLockMetadata> SEMAPHORE_MAP = new ConcurrentHashMap<>();
    final int DEFAULT_SEMAPHORE_PERMITS = 1;

    @Override
    public ILock lock(String key) {
        log.trace("开始加锁【{}】...", key);
        String keyWrapper = getUniqueKey(key);
        // 获取信号量
        SemaphoreLockMetadata lockMetadata = SEMAPHORE_MAP.computeIfAbsent(
                keyWrapper,
                k -> new SemaphoreLockMetadata(new Semaphore(DEFAULT_SEMAPHORE_PERMITS)));
        try {
            // 尝试加锁
            lockMetadata.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            String error = MessageFormat.format("加锁【{0}】失败，发生异常", key);
            log.error(error, e);
            throw new CommonException(error, e);
        }
        log.trace("加锁【{}】成功", key);
        return new ILock(this, key);
    }

    @Override
    public ILock lock(String key, long time, TimeUnit unit) {
        log.trace("开始尝试加锁【{}】...", key);
        String keyWrapper = getUniqueKey(key);
        // 获取信号量
        SemaphoreLockMetadata lockMetadata = SEMAPHORE_MAP.computeIfAbsent(
                keyWrapper,
                k -> new SemaphoreLockMetadata(new Semaphore(DEFAULT_SEMAPHORE_PERMITS)));
        try {
            // 尝试加锁
            if (lockMetadata.tryAcquire(time, unit)) {
                log.trace("尝试加锁【{}】成功", key);
                return new ILock(this, key);
            } else {
                String errorMessage = MessageFormat.format("尝试加锁【{0}】失败，被占用", key);
                log.error(errorMessage);
                throw new CommonException(errorMessage);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            String error = MessageFormat.format("尝试加锁【{0}】失败，发生异常", key);
            log.error(error, e);
            throw new CommonException(error, e);
        }
    }

    @Override
    public void unlock(String key) {
        log.trace("开始解锁【{}】...", key);
        String keyWrapper = getUniqueKey(key);
        SemaphoreLockMetadata lockMetadata = SEMAPHORE_MAP.get(keyWrapper);
        if (lockMetadata != null) {
            try {
                lockMetadata.release();
                log.trace("解锁【{}】成功", key);
            } catch (IllegalStateException e) {
                log.error("解锁失败【{}】, 该信号量已经被释放", key);
            }
        } else {
            log.warn("解锁【{}】失败：未找到对应信号量", key);
        }
    }

    private String getUniqueKey(String key) {
        return String.format("%s:%s", localLockProps.getPrefix(), key);
    }

    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1); // 用于定时清理
    final long MAX_LOCK_EXPIRY_TIME_MS = TimeUnit.MINUTES.toMillis(5);

    // 清理已过期的锁的任务
    private final Runnable cleanupTask = () -> {
        log.trace("开始清理超时Semaphore锁...");
        long currentTime = System.currentTimeMillis();
        SEMAPHORE_MAP.forEach((key, lockMetadata) -> {
            // 信号量恢复默认值且锁已过期，则清理
            int availablePermits = lockMetadata.availablePermits();
            long intervalTimeMs = currentTime - lockMetadata.getTimestamp();
            if (availablePermits == DEFAULT_SEMAPHORE_PERMITS && intervalTimeMs > MAX_LOCK_EXPIRY_TIME_MS) {
                SEMAPHORE_MAP.remove(key);
                log.trace("清理超时Semaphore锁【{}】", key);
            }
        });
        log.trace("清理超时Semaphore锁完成");
    };

    // 启动定时清理任务（可以设置定期执行的清理机制）
    @PostConstruct
    public void startCleanupScheduler() {
        scheduler.scheduleAtFixedRate(cleanupTask, 0, 1, TimeUnit.MINUTES); // 每分钟清理一次
    }

    // 关闭定时清理任务
    @PreDestroy
    public void shutdownCleanup() {
        scheduler.shutdown();
    }
}
