package com.mysoft.leistd.locallock.impl;

import lombok.Getter;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

class SemaphoreLockMetadata {
    private final Semaphore semaphore;
    /**
     * 记录加锁时间
     */
    @Getter
    private long timestamp;

    public SemaphoreLockMetadata(Semaphore semaphore) {
        this.semaphore = semaphore;
        this.timestamp = System.currentTimeMillis();
    }

    public void acquire() throws InterruptedException {
        semaphore.acquire();
        this.timestamp = System.currentTimeMillis();
    }

    public boolean tryAcquire(long timeout, TimeUnit unit) throws InterruptedException {
        if(semaphore.tryAcquire(timeout, unit)){
            this.timestamp = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public void release() {
        semaphore.release();
    }

    public int availablePermits(){
        return semaphore.availablePermits();
    }
}
