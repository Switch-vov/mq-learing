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
public class BatchLargeMessageProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("test-ms-batch-producer");
        producer.setNamesrvAddr("47.99.125.213:9876");
        producer.start();
        String topic = "TopicTest_Batch";
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(topic, "TagA", "OrderID001", "Hello World 1".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        messages.add(new Message(topic, "TagA", "OrderID002", "Hello World 2".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        messages.add(new Message(topic, "TagA", "OrderID003", "Hello World 3".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        messages.add(new Message(topic, "TagA", "OrderID004", "Hello World 4".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        messages.add(new Message(topic, "TagA", "OrderID005", "Hello World 5".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        messages.add(new Message(topic, "TagA", "OrderID006", "Hello World 6".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        messages.add(new Message(topic, "TagA", "OrderID007", "Hello World 7".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        messages.add(new Message(topic, "TagA", "OrderID008", "Hello World 8".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        messages.add(new Message(topic, "TagA", "OrderID009", "Hello World 9".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        messages.add(new Message(topic, "TagA", "OrderID0010", "Hello World 10".getBytes(RemotingHelper.DEFAULT_CHARSET)));
        ListSplitter splitter = new ListSplitter(messages);
        while (splitter.hasNext()) {
            List<Message> splitMessages = splitter.next();
            producer.send(splitMessages);
        }
        producer.shutdown();
    }
}
