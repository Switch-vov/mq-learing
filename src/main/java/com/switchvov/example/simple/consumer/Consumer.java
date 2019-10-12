package com.switchvov.example.simple.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author switch
 * @since 2019/9/21
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        // 创建消费者对象
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test-mq-consumer-group");
        // 设置 name server 地址
        consumer.setNamesrvAddr("47.99.125.213:9876");
        // 订阅
        consumer.subscribe("TopicTest_2", "*");
        // 注册回调
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

        });
        // 启动消费者
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}
