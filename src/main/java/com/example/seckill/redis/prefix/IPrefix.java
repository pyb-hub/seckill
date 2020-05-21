package com.example.seckill.redis.prefix;

/*模板模式*/
/*前缀的上层接口：定义二个功能，一个是获取前缀，一个是获取过期时间*/
public interface IPrefix {

    int getExpireSeconds();

    String getPrefix();
}
