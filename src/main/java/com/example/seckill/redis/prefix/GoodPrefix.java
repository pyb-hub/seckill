package com.example.seckill.redis.prefix;

/*模板模式*/
public class GoodPrefix extends BasePrefix{


    public GoodPrefix(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /*设置网页的redis有效期为60s*/
    public static GoodPrefix goodList = new GoodPrefix(5,"goodList");
    public static GoodPrefix goodDetail = new GoodPrefix(60,"goodDetail");
    /*redis中存秒杀商品的库存：有效期和活动日期一致,假设五天*/
    public static GoodPrefix goodStock = new GoodPrefix(3600*24*5,"goodStock");
    public static GoodPrefix result = new GoodPrefix(3600*24*5,"seckillResult");
    /*设置秒杀网页的path的prefix*/
    public static GoodPrefix path = new GoodPrefix(60,"seckillPath");
    /*接口防刷的key：5秒内只能刷5次接口*/
    public static GoodPrefix access = new GoodPrefix(5,"access");

}
