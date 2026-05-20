package com.mysoft.leistd.dstblock.impl;

import com.mysoft.leistd.dstblock.IDistributedLock;
import com.mysoft.leistd.dstblock.ILock;
import com.mysoft.leistd.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * 默认分布式锁服务（Redis）实现
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SpringRedisDstbLockImpl implements IDistributedLock {
    final RedisLockRegistry redisLockRegistry;

    @Override
    public ILock lock(String key) {
        log.debug("开始加锁【{}】...", key);
        Lock redisLock = redisLockRegistry.obtain(key);
        redisLock.lock();
        log.debug("加锁【{}】成功", key);
        return new ILock(this, key);
    }

    @Override
    public ILock lock(String key, long time, TimeUnit unit) {
        if (time == 0) {
            return lock(key);
        }
        log.debug("开始尝试加锁【{}】...", key);
        try {
            Lock redisLock = redisLockRegistry.obtain(key);
            if (redisLock.tryLock(time, unit)) {
                log.debug("尝试加锁【{}】成功", key);
                return new ILock(this, key);
            } else {
                log.error("尝试加锁【{}】失败：被占用", key);
                throw new CommonException(MessageFormat.format("尝试加锁【{0}】失败，请稍后重试", key));
            }
        } catch (InterruptedException e) {
            String error = MessageFormat.format(
                    "尝试加锁【{0}】失败，发生异常",
                    key);
            log.error(error, e);
            throw new CommonException(error, e);
        }
    }

    @Override
    public void unlock(String key) {
        log.debug("开始解锁【{}】...", key);
        Lock redisLock = redisLockRegistry.obtain(key);
        redisLock.unlock();
        log.debug("解锁【{}】成功", key);
    }
}
