package com.switchvov.cache.priority;

import com.switchvov.cache.BaseLruCache;
import com.switchvov.cache.LruNode;
import com.switchvov.cache.Storage;

/**
 * 优先队列 Lru Cache 实现
 * <p>
 * 采用优先队列 + Hash 实现
 * <p>
 * 查找时间复杂度为常数，添加、删除时间复杂度为log(n)
 *
 * @param <V> 必须继承 Priority
 * @author switch
 * @since 2019/10/24
 */
public class PriorityLruCache<K, V extends Priority> extends BaseLruCache<K, V> {
    private PriorityQueue<PriorityLruNode<K, V>> queue;

    public PriorityLruCache(int capacity, Storage<K, V> lowSpeedStorage) {
        super(capacity, lowSpeedStorage);
        queue = new PriorityQueue<>();
    }

    @Override
    protected LruNode<K, V> node(K key, V value) {
        PriorityLruNode<K, V> node = new PriorityLruNode<>();
        node.setKey(key);
        node.setValue(value);
        node.setPriority(value.getPriority());

        // 入队
        queue.enque(node);
        return node;
    }

    @Override
    protected K overHandle() {
        PriorityLruNode<K, V> node = queue.deque();
        return node.getKey();
    }
}
