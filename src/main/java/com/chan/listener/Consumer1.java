package com.chan.listener;

import com.alibaba.fastjson.JSONObject;
import com.chan.util.rabbitConfig.ConsumerConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer1 {

    @RabbitListener(queues = ConsumerConfig.QUEUE_NAME)
    public void RabbitListener1(JSONObject msg){
        System.out.println("Consumer1 收到消息："+msg);
    }

}
