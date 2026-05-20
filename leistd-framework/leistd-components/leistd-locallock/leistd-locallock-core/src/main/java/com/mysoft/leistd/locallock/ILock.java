package com.mysoft.leistd.locallock;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ILock implements AutoCloseable {
    final ILocalLock localLock;
    final String lockKey;

    @Override
    public void close() {
        localLock.unlock(lockKey);
    }
}
