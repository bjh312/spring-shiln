package com.shiln.order.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.impl.recovery.AutorecoveringChannel;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AmqpConfig {

    @Bean
    Queue incomingQueue() {
        return QueueBuilder.durable(Constants.INCOMING_QUEUE_NAME)
                //.withArgument("x-dead-letter-exchange", "bao-x-dead-letter-exchange")
                //.withArgument("x-dead-letter-routing-key", Constants.DEAD_LETTER_QUEUE_NAME)
                .build();
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}
