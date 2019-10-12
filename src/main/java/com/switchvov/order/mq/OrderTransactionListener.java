package com.switchvov.order.mq;

import com.switchvov.order.Order;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author switch
 * @since 2019/9/27
 */
public class OrderTransactionListener implements TransactionListener {
    private ConcurrentHashMap<String, AtomicInteger> orderQueryCountMapping = new ConcurrentHashMap<>();

    private final static int MAX_COUNT = 5;

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        return LocalTransactionState.UNKNOW;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        String orderId = msg.getUserProperty(Order.ORDER_ID_STR);

        if (query(orderId)) {
            return LocalTransactionState.COMMIT_MESSAGE;
        }
        return rollbackOrUnknown(orderId);
    }

    private LocalTransactionState rollbackOrUnknown(String orderId) {
        AtomicInteger queryCount = orderQueryCountMapping.get(orderId);
        if (queryCount == null) {
            queryCount = new AtomicInteger(0);
        }

        if (queryCount.incrementAndGet() > MAX_COUNT) {
            orderQueryCountMapping.remove(orderId);
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        orderQueryCountMapping.put(orderId, queryCount);
        return LocalTransactionState.UNKNOW;
    }

    private Boolean query(String orderId) {
        // 查询该订单状态
        return Boolean.TRUE;
    }
}
