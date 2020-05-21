package com.example.seckill.redis.prefix;


/*模板模式：前缀的抽象类，定义共同的行为*/
public abstract class BasePrefix implements IPrefix{

    private int expireSeconds;

    private String prefix;

    public BasePrefix(String prefix) {//-1代表永不过期
        this(-1, prefix);
    }

    public BasePrefix( int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    /*获取过期时间*/
    public  int getExpireSeconds(){//默认-1代表永不过期
        return expireSeconds;
    }
    /*获取前缀*/
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className+":" + prefix;
    }
}
