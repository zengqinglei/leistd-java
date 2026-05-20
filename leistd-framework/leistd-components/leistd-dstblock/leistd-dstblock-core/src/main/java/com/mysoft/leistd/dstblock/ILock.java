package com.mysoft.leistd.dstblock;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ILock implements AutoCloseable {
    final IDistributedLock distributedLock;
    final String lockKey;

    @Override
    public void close() {
        distributedLock.unlock(lockKey);
    }
}
