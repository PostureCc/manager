package com.chan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chan.service.ProducerService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("producerService")
public class ProducerServiceImpl implements ProducerService{

    @Autowired
//    @Qualifier("messageTemplate")
    private AmqpTemplate messageTemplate;

    @Override
    public void sendMsg(String queueName, JSONObject msg) {
        System.out.println("ProducerServiceImpl queueName:"+queueName+" msg:"+msg);
//        rabbitTemplate.setQueue(queueName);
        messageTemplate.convertAndSend(queueName,msg);
    }

}
