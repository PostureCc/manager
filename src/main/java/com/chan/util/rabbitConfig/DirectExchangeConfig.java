package com.chan.util.rabbitConfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectExchangeConfig {

    @Autowired
    private RabbitConfig rabbitConfig;

    public static final String EXCHANGE_NAME = "directExchange";

    public static final String DIRECT_ROUTING_KEY = "directExchangeKey";

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue directQueu(){return new Queue(RabbitConfig.QUEUE_NAME);}

    /**创建交换机绑定队列*/
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(rabbitConfig.directQueue()).to(directExchange()).with(DIRECT_ROUTING_KEY);
    }

}
