package com.example.seckill.redis.prefix;

/*模板模式*/
public class UserPrefix extends BasePrefix{


    public UserPrefix(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /*作为网站的分布式session：设置redis的有效期为2天，后面的cookie的过期时间等于redis的*/
    public static UserPrefix token = new UserPrefix(3600*24*2,"token");
    /*这个是对象级别缓存，永不过期，和上面网站session的userPredix不一样*/
    public static UserPrefix userKey = new UserPrefix(-1,"userKey");

}
