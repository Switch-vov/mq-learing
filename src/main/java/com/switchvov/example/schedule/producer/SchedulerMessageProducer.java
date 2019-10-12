package com.switchvov.example.schedule.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author switch
 * @since 2019/9/21
 */
public class SchedulerMessageProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("test-mq-scheduler-producer");
        producer.setNamesrvAddr("47.99.125.213:9876");
        producer.start();
        int totalMessagesToSend = 100;
        for (int i = 0; i < totalMessagesToSend; i++) {
            Message msg = new Message("TestTopic_Scheduler", ("Hello Scheduled Message " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            // 设置延迟等级
            // 延时等级:1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            msg.setDelayTimeLevel(3);
            producer.send(msg);
        }
        producer.shutdown();
    }
}
