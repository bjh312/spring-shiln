package com.shiln.order.consumer;

import com.shiln.order.bean.Order;
import com.shiln.order.config.Constants;
import com.shiln.order.config.InsufficientFundsException;
import com.shiln.order.service.OrderService;
import com.shiln.user.bean.User;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


/**
 * Created by baojunhu on 2018/3/13.
 * 订单消费者
 */

@Component
public class OrderConsumer {

    @Autowired
    private OrderService orderService;

    @RabbitListener(queues = Constants.INCOMING_QUEUE_NAME)
    public void orderMonitor(@Payload User user) throws InsufficientFundsException{
        try{

            System.out.println("消费者接受消息:"+user);
            //插入order表
            Order order = new Order();
            order.setUserID(user.getUserID());
            order.setUserName(user.getUserName());
            order.setOrderName("测试单子");
            order.setOrderAmount(new BigDecimal(100));
            order.setOrderRemark("测试123");
            orderService.add(order);
            System.out.println("插入订单orderID"+order.getOrderID());
        }catch (AmqpRejectAndDontRequeueException ae){
            throw new AmqpRejectAndDontRequeueException("consume failed",ae);
        }catch (Exception e){
            //log.error("error occur", e);
            e.printStackTrace();
        }
    }

}
