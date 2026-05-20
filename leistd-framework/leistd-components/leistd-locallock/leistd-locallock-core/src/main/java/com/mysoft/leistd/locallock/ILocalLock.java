package com.mysoft.leistd.locallock;

import java.util.concurrent.TimeUnit;

/**
 * 本地锁服务
 */
public interface ILocalLock {
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
