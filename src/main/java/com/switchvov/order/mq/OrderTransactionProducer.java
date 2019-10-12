package com.switchvov.order.mq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;

import java.util.concurrent.*;

/**
 * @author switch
 * @since 2019/9/27
 */
public class OrderTransactionProducer {
    private TransactionMQProducer producer;

    {
        producer = new TransactionMQProducer("order_transaction_producer");
        producer.setNamesrvAddr("47.99.125.213:9876");
        producer.setTransactionListener(new OrderTransactionListener());
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("client-transaction-msg-check-thread");
                return thread;
            }
        });
        producer.setExecutorService(executorService);
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    private OrderTransactionProducer() {
    }

    public OrderTransactionProducer getInstance() {
        return new OrderTransactionProducer();
    }

    public TransactionMQProducer getProducer() {
        return producer;
    }

    public void destory() {
        producer.shutdown();
    }
}
