package com.chan.controller;

import com.chan.service.ProducerService;
import com.chan.util.rabbitConfig.DirectExchangeConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.alibaba.fastjson.JSONObject;
import com.chan.util.rabbitConfig.RabbitConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api("RabbitMQ")
@Controller
public class RabbitController {

    @Autowired
    @Qualifier("producerService")
    private ProducerService producerService;

    @ApiOperation("Test Method1")
    @ResponseBody
    @RequestMapping("/demo1")
    public String demo1() {
        JSONObject json = new JSONObject(3);
        json.put("key1", "value1");
        json.put("key2", "value2");
        producerService.sendMsg(RabbitConfig.QUEUE_NAME, json);
        return "";
    }

    @ApiOperation("Test Method2")
    @ResponseBody
    @RequestMapping("/demo2")
    public String demo2() throws InterruptedException {
        for (int i = 1; i <= 10; i++) {
            JSONObject json = new JSONObject(11);
            json.put("key" + i, "value" + i);
            producerService.sendMsgByDirect(DirectExchangeConfig.EXCHANGE_NAME, DirectExchangeConfig.DIRECT_ROUTING_KEY, json);
            Thread.sleep(300L);
        }
        return "";
    }

}
