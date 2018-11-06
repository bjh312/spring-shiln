package com.shiln.user.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AmqpConfig {

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(Constants.EXCHANGE_NAME);
    }

    @Bean
    Queue incomingQueue() {
        return QueueBuilder.durable(Constants.INCOMING_QUEUE_NAME)
                //.withArgument("x-dead-letter-exchange", "bao-x-dead-letter-exchange")
                //.withArgument("x-dead-letter-routing-key", Constants.DEAD_LETTER_QUEUE_NAME)
                .build();
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(incomingQueue()).to(exchange()).with(Constants.ROUTING_KEY_NAME);
    }

//    @Bean
//    Queue deadLetterQueue() {
//        return QueueBuilder.durable(Constants.DEAD_LETTER_QUEUE_NAME).build();
//    }

    /**
     * 消息体序列化
     * @return
     */
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
