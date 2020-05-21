package com.example.seckill.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.example.seckill.controller.GoodController;
import com.example.seckill.domain.Order;
import com.example.seckill.domain.User;
import com.example.seckill.redis.RedisService;
import com.example.seckill.redis.prefix.GoodPrefix;
import com.example.seckill.result.CodeMsg;
import com.example.seckill.result.Result;
import com.example.seckill.service.impl.SeckillService;
import com.example.seckill.vo.MQMsgVo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*消息的接受者*/
@Service
public class MQReceiver {

    @Autowired
    private RedisService redisService;

    @Autowired
    private SeckillService seckillService;

    /*接受哪个队列的消息*/
    @RabbitListener(queues = MQConfig.SECKILL_QUEUE)
    public void receive(String message) {
        MQMsgVo mqMsgVo = JSON.parseObject(message, MQMsgVo.class);
        User user = mqMsgVo.getUser();
        Long goodId = mqMsgVo.getGoodId();
        /*秒杀执行*/
        try {
            Order order = seckillService.seckill(user, goodId);
            return;
        } catch (Exception e) {
            //秒杀失败
            //e.printStackTrace();
            /*redis回滚，增加库存*/
            redisService.incr(GoodPrefix.goodStock, goodId + "");
            GoodController.productSoldOutMap.put(goodId,false);
            return;
        }
    }


}
