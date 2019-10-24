package com.switchvov.lock;

import java.io.Closeable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author switch
 * @since 2019/10/24
 */
public class CloseableLockProxy implements Closeable {
    private static final Lock LOCK = new ReentrantLock();


    @Override
    public void close() {
        LOCK.unlock();
        System.out.println(LOCK + "锁已释放");
    }

    public void lock() {
        LOCK.lock();
        System.out.println(LOCK + "已上锁");
    }

    public static void main(String[] args) {
        IntStream.range(0, 5)
                .mapToObj(index -> CompletableFuture.runAsync(() -> {
                    try (CloseableLockProxy lockProxy = new CloseableLockProxy()) {
                        lockProxy.lock();
                        System.out.println("Hello");
                    }
                }))
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
}
