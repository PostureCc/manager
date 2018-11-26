package com.chan.listener;

import com.alibaba.fastjson.JSONObject;
import com.chan.util.rabbitConfig.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer1 {

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void RabbitListener1(JSONObject msg){
        System.out.println("RabbitListener1 收到消息："+msg);
    }

    @RabbitListener(queues = RabbitConfig.DIRECT_QUEUE)
    public void RabbitListener2(JSONObject msg){
        try {
            System.out.println("RabbitListener2 收到消息："+msg);
            Thread.sleep(200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = RabbitConfig.DIRECT_QUEUE)
    public void RabbitListener2_1(JSONObject msg){
        try {
            System.out.println("RabbitListener2_1 收到消息："+msg);
            Thread.sleep(200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
