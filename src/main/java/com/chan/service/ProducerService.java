package com.chan.service;

import com.alibaba.fastjson.JSONObject;

public interface ProducerService {

    void sendMsg(String queueName, JSONObject msg);

    void sendMsgByDirect(String exchangeName,String routingKey, JSONObject msg);

}
