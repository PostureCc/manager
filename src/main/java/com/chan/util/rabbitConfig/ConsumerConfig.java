package com.chan.util.rabbitConfig;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerConfig {

    public static final String QUEUE_NAME = "queue1_1";

    @Autowired
//    @Qualifier("rabbitConfig")
    private RabbitConfig rabbitConfig;

    @Bean
    public Queue queue(){return new Queue(QUEUE_NAME);}
}
