package com.switchvov.cache.linked;

import com.switchvov.cache.priority.PriorityLruNode;
import lombok.Data;

/**
 * @author switch
 * @since 2019/10/23
 */
@Data
public class DuLinkLruNode<K, V> extends PriorityLruNode<K, V> {
    private DuLinkLruNode<K, V> prev;
    private DuLinkLruNode<K, V> next;
}
