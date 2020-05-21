package com.example.seckill.controller;

import com.example.seckill.rabbitmq.MQSend;
import com.example.seckill.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    @Autowired
    private MQSend send;

    /*@RequestMapping("/mq")
    @ResponseBody
    public Result<String> MQ(){
        send.send("hello mq");
        return Result.success("suc");
    }*/
}
