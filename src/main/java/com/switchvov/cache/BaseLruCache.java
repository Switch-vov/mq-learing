package com.switchvov.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基础 LruCache 类
 * <p>
 * 重新封装了一层，将查找缓存的存储结构固定为 ConcurrentHashMap
 *
 * @author switch
 * @since 2019/10/24
 */
public abstract class BaseLruCache<K, V> extends LruCache<K, V> {
    /**
     * hash mapping
     */
    protected Map<K, LruNode<K, V>> nodeMapping;


    /**
     * 超过容量后删除的对象数
     */
    protected int overDeleteCount;

    /**
     * 设置缓存时加上锁，防止并发设置产生的问题
     */
    private Lock lock;

    public BaseLruCache(int capacity, Storage<K, V> lowSpeedStorage) {
        super(capacity, lowSpeedStorage);
        nodeMapping = new ConcurrentHashMap<>();
        lock = new ReentrantLock();
        // 容量1:1;容量[2,9]:容量的一半;容量[10,99]:5;容量[100,+∞]:10
        overDeleteCount = 10;
        if (capacity < overDeleteCount) {
            overDeleteCount = capacity / 2 > 0 ? capacity / 2 : 1;
        } else if (capacity < overDeleteCount * overDeleteCount) {
            overDeleteCount = overDeleteCount / 2;
        }
    }

    @Override
    public V get(K key) {
        if (key == null) {
            return null;
        }

        LruNode<K, V> node = nodeMapping.get(key);

        V value = null;
        if (node != null) {
            value = node.getValue();
        }

        if (value == null) {
            value = lowSpeedStorage.get(key);
            // 设置缓存
            if (value != null) {
                setCache(key, value);
            }
        }
        return value;
    }

    private void setCache(K key, V value) {
        try {
            lock.lock();
            LruNode<K, V> node = node(key, value);
            // 加入 mapping
            nodeMapping.putIfAbsent(key, node);
            if (nodeMapping.size() > capacity) {
                // 超过容量处理
                for (int i = 0; i < overDeleteCount; i++) {
                    K deleteKey = overHandle();
                    if (deleteKey != null) {
                        nodeMapping.remove(deleteKey);
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }

    protected abstract LruNode<K, V> node(K key, V value);

    protected abstract K overHandle();
}
