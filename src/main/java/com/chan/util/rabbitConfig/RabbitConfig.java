package com.chan.util.rabbitConfig;


import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {

    public static final String QUEUE_NAME = "queue1_1";

    public static final String DIRECT_QUEUE = "direct_queue";

    public static final String PC_QUEUE = "pc_queue";

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

    @Bean
    public Queue queue(){return new Queue(QUEUE_NAME);}

    @Bean
    public Queue directQueue(){return new Queue(DIRECT_QUEUE);}

    @Bean
    public Queue pcQueue(){return new Queue(PC_QUEUE);}

}
