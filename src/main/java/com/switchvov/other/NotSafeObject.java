package com.switchvov.other;

import java.util.ConcurrentModificationException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 从{@link org.apache.kafka.clients.consumer.KafkaConsumer}中抽取
 * <p>
 * 通过硬件原语实现了相对于锁，更细粒度的并发控制功能
 * <p>
 * 通过该实现，可以显示提示调用者某类不支持并发调用
 *
 * @author switch
 * @since 2019/10/25
 */
public class NotSafeObject {
    private static final long NO_CURRENT_THREAD = -1L;

    /**
     * currentThread holds the threadId of the current thread accessing NotSafeObject
     * and is used to prevent multi-threaded access
     */
    private final AtomicLong currentThread = new AtomicLong(NO_CURRENT_THREAD);

    /**
     * 通过该对象实现 获取该对象的线程可重入
     */
    private final AtomicInteger refcount = new AtomicInteger(0);

    private void acquire() {
        long threadId = Thread.currentThread().getId();
        // 当前没有线程持有该对象时，使用硬件原语 CAS 更新线程 ID 为当前线程 ID
        // 否则，其他线程准备获取该对象，则抛出并发修改异常；当前线程获取该对象时，则进入之后的语句
        if (threadId != currentThread.get() && !currentThread.compareAndSet(NO_CURRENT_THREAD, threadId)) {
            throw new ConcurrentModificationException("NotSafeObject is not safe for multi-threaded access");
        }
        refcount.incrementAndGet();
    }

    private void release() {
        if (refcount.decrementAndGet() == 0) {
            currentThread.set(NO_CURRENT_THREAD);
        }
    }
}
