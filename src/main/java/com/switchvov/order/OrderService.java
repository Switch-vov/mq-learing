package com.switchvov.order;

import com.alibaba.fastjson.JSON;
import com.switchvov.order.mq.OrderTransactionProducer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;

/**
 * @author switch
 * @since 2019/9/27
 */
public class OrderService {
    private OrderTransactionProducer producer;

    public OrderService(OrderTransactionProducer producer) {
        this.producer = producer;
    }

    public void orderService(Order order) {
        // 执行下单业务流程

        // 发送事务消息
        TransactionMQProducer producer = this.producer.getProducer();
        String messageBody = JSON.toJSONString(order);
        try {
            Message msg = new Message("OrderTransactionTopic", "Order", order.getId(),
                    messageBody.getBytes(RemotingHelper.DEFAULT_CHARSET));
            msg.putUserProperty(Order.ORDER_ID_STR, order.getId());
            SendResult sendResult = producer.sendMessageInTransaction(msg, null);
            System.out.println(sendResult);
        } catch (UnsupportedEncodingException | MQClientException e) {
            // 异常处理
        }
    }
}
