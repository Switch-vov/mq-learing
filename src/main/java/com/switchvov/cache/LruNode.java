package com.switchvov.cache;

import lombok.Data;

/**
 * @author switch
 * @since 2019/10/24
 */
@Data
public class LruNode<K, V> {
    private K key;
    private V value;
}
