package com.switchvov.example.batch.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author switch
 * @since 2019/9/21
 */
public class BatchSmallMessageProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("test-ms-batch-producer");
        producer.setNamesrvAddr("47.99.125.213:9876");
        producer.start();
        String topic = "TopicTest_Batch";
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(topic, "TagA", "OrderID001", "Hello World 1".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        messages.add(new Message(topic, "TagA", "OrderID002", "Hello World 2".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        messages.add(new Message(topic, "TagA", "OrderID003", "Hello World 3".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        producer.send(messages);
        producer.shutdown();
    }
}
