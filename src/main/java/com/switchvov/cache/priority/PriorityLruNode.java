package com.switchvov.cache.priority;

import com.switchvov.cache.LruNode;
import lombok.Data;

/**
 * @author switch
 * @since 2019/10/24
 */
@Data
public class PriorityLruNode<K, V> extends LruNode<K, V> implements Comparable<PriorityLruNode<K, V>> {
    private Integer priority;

    @Override
    public int compareTo(PriorityLruNode<K, V> node) {
        return priority.compareTo(node.getPriority());
    }
}
