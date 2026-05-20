package com.mysoft.leistd.dstblock;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁服务
 */
public interface IDistributedLock {
    /**
     * 加锁
     */
    ILock lock(String key);

    /**
     * 加锁（自动解锁）
     */
    ILock lock(String key, long time, TimeUnit unit);

    /**
     * 解锁
     */
    void unlock(String key);
}
