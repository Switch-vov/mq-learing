package com.switchvov.example.filter.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author switch
 * @since 2019/9/21
 */
public class FilterMessageProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("test-ms-filter-producer");
        producer.setNamesrvAddr("47.99.125.213:9876");
        producer.start();
        for (int i = 0; i < 100; i++) {
            Message msg = new Message("TopicTest_Filter", "TagA", ("Hello RocketMQ" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            msg.putUserProperty("index", String.valueOf(i));
            SendResult sendResult = producer.send(msg);
            System.out.println("sendResult: " + sendResult);
        }
        producer.shutdown();
    }
}
