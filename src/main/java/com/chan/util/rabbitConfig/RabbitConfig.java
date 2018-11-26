package com.chan.util.rabbitConfig;


import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {

    @Bean
    public CachingConnectionFactory getConnection(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setPort(6379);
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        return  connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin(){return new RabbitAdmin(getConnection());}

    @Bean
    public RabbitTemplate rabbitTemplate(){return new RabbitTemplate(getConnection());}

}
