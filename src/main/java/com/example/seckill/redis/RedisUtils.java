package com.example.seckill.redis;


import com.example.seckill.redis.prefix.IPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis操作工具类，方便操作redistemplate
 * (基于RedisTemplate)
 */
@Component
public class RedisUtils {

    @Autowired
    /*只有String，String类型能注入，需要其他的话要编写配置类自己注入*/
    private StringRedisTemplate redisTemplate;

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public String get(IPrefix prefix, final String key) {
        /*生成复合的key*/
        String realKey = prefix.getPrefix()+key;
        return redisTemplate.opsForValue().get(realKey);
    }

    /**
     * 写入缓存
     */
    public boolean set(IPrefix prefix, final String key, String value) {
        boolean result = false;
        try {
            /*生成复合的key*/
            String realKey = prefix.getPrefix()+key;
            if (prefix.getExpireSeconds()>=0){
                /*设置过期时间*/
                redisTemplate.opsForValue().set(realKey, value,prefix.getExpireSeconds(),TimeUnit.SECONDS);
            }else {
                redisTemplate.opsForValue().set(realKey, value);
            }

            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 更新缓存
     */
    public boolean getAndSet(IPrefix prefix, final String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().getAndSet(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除缓存
     */
    public boolean delete(IPrefix prefix, final String key) {
        boolean result = false;
        try {
            /*生成复合的key*/
            String realKey = prefix.getPrefix()+key;
            redisTemplate.delete(realKey);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /*判断是否存在*/
    public boolean isExist(IPrefix prefix, final String key){
        /*生成复合的key*/
        String realKey = prefix.getPrefix()+key;
        return redisTemplate.hasKey(realKey);
    }

    /*value增加值*/
    public Long incr(IPrefix prefix, final String key){
        /*生成复合的key*/
        String realKey = prefix.getPrefix()+key;
        return redisTemplate.opsForValue().increment(realKey);
    }

    /*value减少值*/
    public Long decr(IPrefix prefix, final String key){
        /*生成复合的key*/
        String realKey = prefix.getPrefix()+key;
        return redisTemplate.opsForValue().decrement(realKey);
    }

}
