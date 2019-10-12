package com.switchvov.example.simple.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author switch
 * @since 2019/9/21
 */
public class SyncProducer {
    public static void main(String[] args) throws Exception {
        // 创建生产者对象
        DefaultMQProducer producer = new DefaultMQProducer("test-mq-producer-group");
        // 设置 name server 地址
        producer.setNamesrvAddr("47.99.125.213:9876");
        // 启动生产者
        producer.start();
        // 组装消息并发送
        for (int i = 0; i < 100; i++) {
            Message msg = new Message("TopicTest_2", "TagA", ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        // 关闭生产者
        producer.shutdown();
    }

}
