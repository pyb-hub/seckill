package com.example.seckill.redis;


import com.example.seckill.redis.prefix.IPrefix;
import com.example.seckill.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisUtils redisUtils;

    public String get (IPrefix prefix, String key){
        return redisUtils.get(prefix,key);
    }

    public Boolean set (IPrefix prefix, String key, String value){
        return redisUtils.set(prefix,key,value);
    }

    public Boolean isExist(IPrefix prefix, String key){
        return redisUtils.isExist(prefix,key);
    }

    public Long incr(IPrefix prefix, String key){
        return redisUtils.incr(prefix,key);
    }

    public Long decr(IPrefix prefix, String key){
        return redisUtils.decr(prefix,key);
    }

}
