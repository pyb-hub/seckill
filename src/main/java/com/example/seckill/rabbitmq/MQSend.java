package com.example.seckill.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.example.seckill.vo.MQMsgVo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*消息的发送者*/
@Service
public class MQSend {

    /*rabbitMq的类*/
    @Autowired
    private AmqpTemplate amqpTemplate;

    /*简单模式:把用户信息和商品发送到队列中*/
    public void send(MQMsgVo message) {
        String msg = JSON.toJSONString(message);
        System.out.println("send message:"+msg);
        /*发送消息到队列*/
        amqpTemplate.convertAndSend(MQConfig.SECKILL_QUEUE, msg);
    }

    /*public void send(Object message) {
        String msg = JSON.toJSONString(message);
        System.out.println("send message:"+msg);
        *//*发送消息到队列*//*
        amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
    }*/
}
