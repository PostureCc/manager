package com.chan.util.rabbitConfig;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectExchangeConfig {

    @Bean
    public DirectExchange directExchange(){
        return  new DirectExchange("directExchange");
    }

    @Bean
    public Queue directQueu(){return new Queue("queue1");}

}
