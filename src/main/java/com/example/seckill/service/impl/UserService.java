package com.example.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.seckill.dao.UserDao;
import com.example.seckill.domain.User;
import com.example.seckill.redis.RedisService;
import com.example.seckill.redis.prefix.UserPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserService{

    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;

    public static final String COOKI_NAME_TOKEN = "token";


    /*增加对象缓存*/
    public User findById(Long id) {
        /*从redis取，注意和分布式session里面redis的key区别开，那个是为了登录验证的，有过期时间，这个对象不设置过期时间，更新的时候要注意更新缓存*/
        String u = redisService.get(UserPrefix.userKey, "" + id);
        User user = (User) JSON.parse(u);
        if (user != null){
            return user;
        }
        /*redis中没有数据，从数据库取*/
        User user2 = userDao.findById(id);
        if (user2 != null){
            redisService.set(UserPrefix.userKey,""+id, JSON.toJSONString(user2));
        }

        return user2;
    }


    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }


    public User findByUsernameAndPassword(String username, String password) {
        return userDao.findByUsernameAndPassword(username,password);
    }


    /*生成分布式session，用cookie和redis实现*/
    public void addCookie(HttpServletResponse response, User user,String token) {

        /*拼接UserPrefix.token和token作为redis的key，value存放用户信息,转化为jason格式*/
        /*这里同时也设置了redis的有效期*/
        redisService.set(UserPrefix.token,token,JSON.toJSONString(user));

        /*存放redis的key在cookie中，可以取redis的user对象*/
        Cookie cookie = new Cookie(COOKI_NAME_TOKEN,token);
        /*设置过期时间和session一致*/
        cookie.setMaxAge(UserPrefix.token.getExpireSeconds());
        /*cookie的位置：网站的根目录*/
        cookie.setPath("/");
        /*把cookie信息传到客户端*/
        response.addCookie(cookie);
    }


    /*获取并更新有效期分布式session的user*/
    public User getByToken(String token, HttpServletResponse response) {
        String userString = redisService.get(UserPrefix.token, token);
        //将jason字符串转换成对象
        User user = JSONObject.parseObject(userString,User.class);

        /*更新session在cookie的key和redis里面的内容的有效期*/
        userService.addCookie(response,user,token);
        return user;
    }

}
