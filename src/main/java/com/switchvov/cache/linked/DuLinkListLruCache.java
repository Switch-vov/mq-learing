package com.switchvov.cache.linked;

import com.switchvov.cache.BaseLruCache;
import com.switchvov.cache.LruNode;
import com.switchvov.cache.Storage;

/**
 * 链表 Lru Cache 实现
 * <p>
 * 采用双向链表 + Hash 实现
 * <p>
 * 查找、添加、删除时间复杂度都为常数
 *
 * @author switch
 * @since 2019/10/23
 */
public class DuLinkListLruCache<K, V> extends BaseLruCache<K, V> {

    /**
     * 双向链表 头
     */
    private DuLinkLruNode<K, V> head;

    /**
     * 双向链表 尾
     */
    private DuLinkLruNode<K, V> tail;


    public DuLinkListLruCache(int capacity, Storage<K, V> lowSpeedStorage) {
        super(capacity, lowSpeedStorage);
        head = tail = new DuLinkLruNode<>();
    }

    @Override
    protected LruNode<K, V> node(K key, V value) {
        DuLinkLruNode<K, V> node = new DuLinkLruNode<>();
        node.setKey(key);
        node.setValue(value);
        node.setNext(null);

        // 将 head 赋给变量，防止 head 指向对象更新
        DuLinkLruNode<K, V> prevNode = head;
        prevNode.setNext(node);
        node.setPrev(prevNode);

        // 将 head 指向该对象
        head = node;
        return node;
    }

    @Override
    protected K overHandle() {
        K key = tail.getKey();
        // 双向链表尾指针后移
        tail = tail.getNext();
        return key;
    }
}
