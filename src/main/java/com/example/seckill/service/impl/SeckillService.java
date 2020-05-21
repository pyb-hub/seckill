package com.example.seckill.service.impl;

import com.example.seckill.domain.Order;
import com.example.seckill.domain.OrderSeckill;
import com.example.seckill.domain.User;
import com.example.seckill.redis.RedisService;
import com.example.seckill.redis.prefix.GoodPrefix;
import com.example.seckill.result.CodeMsg;
import com.example.seckill.util.UUIDUtils;
import com.example.seckill.vo.GoodVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class SeckillService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodService goodService;

    @Transactional
    public Order seckill(User user, long goodId) {
        //查询商品库存，可以用redis优化
        GoodVo goodvo = goodService.getGoodsVoByGoodsId(goodId);

        if (goodvo.getStockSeckill() <= 0) {
            redisService.set(GoodPrefix.result,goodId+"","-1");
            throw new RuntimeException(CodeMsg.SECKILL_FAIL.getMsg());
        }

        /*防止二次秒杀,这里优化可以把订单加入redis，减少数据库访问*/
        /*OrderSeckill order1 = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodId);
        if (order1 != null) {
            throw new RuntimeException(CodeMsg.SECKILL_REPEATE.getMsg());
        }*/

        /*秒杀成功*/
        /*创建订单*/
        Order order = orderService.createOrder(user, goodvo);

        /*减库存*/
        int updateNum = goodService.reduceStock(goodvo);
        if (updateNum <= 0) {
            //数据库update失败返回数为0，防止重复下订单，超卖
            redisService.set(GoodPrefix.result,goodId+"","-1");
            throw new RuntimeException(CodeMsg.SECKILL_FAIL.getMsg());
        }
        return order;
    }

    /*在数据库，查看结果:-1代表没成功，0代表还在等待中，成功返回订单id*/
    public Long getResult(User user, Long goodId){
        OrderSeckill order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodId);
        if (order != null){
            return order.getOrderId();
        }else {
            /*在redis内存的标记，判断状态，是卖完了，还是在队列等待*/
            String result = redisService.get(GoodPrefix.result, goodId + "");
            if ("-1".equals(result)){
                return -1L;
            }else {
                return 0L;
            }
        }

    }


    public String createPath(User user, long goodId) {
        String path = UUIDUtils.uuid()+"_"+user.getId()+"_"+goodId;
        redisService.set(GoodPrefix.path,user.getId()+"_"+goodId,path);
        return path;
    }

    public Boolean checkPath(User user, long goodId,String path) {
        String p = redisService.get(GoodPrefix.path, user.getId() + "_" + goodId);
        if (StringUtils.isEmpty(p)){
            return false;
        }else return p.equals(path);
    }
}
