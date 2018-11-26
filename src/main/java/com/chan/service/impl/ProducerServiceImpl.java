package com.chan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chan.service.ProducerService;
import com.chan.util.rabbitConfig.DirectExchangeConfig;
import com.chan.util.rabbitConfig.RabbitConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("producerService")
public class ProducerServiceImpl implements ProducerService {

    @Autowired
//    @Qualifier("messageTemplate")
    private AmqpTemplate messageTemplate;

    @Autowired
//    @Qualifier("rabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMsg(String queueName, JSONObject msg) {
        System.out.println("ProducerServiceImpl queueName:" + queueName + " msg:" + msg);
//        rabbitTemplate.setQueue(queueName);
        messageTemplate.convertAndSend(queueName, msg);
    }

    @Override
    public void sendMsgByDirect(String exchangeName, String routingKey, JSONObject msg) {
        System.out.println("ProducerServiceImpl exchangeName:" + exchangeName + " routingKey:" + routingKey + " msg:" + msg);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, msg);
    }

//    @Override
//    public void sendMsgByPc(String exchangeName, String routingKey, JSONObject msg) {
//        System.out.println("ProducerServiceImpl exchangeName:" + exchangeName + " routingKey:" + routingKey + " msg:" + msg);
//        rabbitTemplate.convertAndSend(exchangeName, routingKey, msg);
//    }

}
