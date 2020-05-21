package com.example.seckill.controller;

import com.alibaba.fastjson.JSON;
import com.example.seckill.domain.User;
import com.example.seckill.redis.RedisService;
import com.example.seckill.redis.prefix.UserPrefix;
import com.example.seckill.result.CodeMsg;
import com.example.seckill.result.Result;
import com.example.seckill.service.impl.UserService;
import com.example.seckill.util.MD5Utils;
import com.example.seckill.util.UUIDUtils;
import com.example.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;


    /*跳转到login界面*/
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    /*login提交的处理*/
    @PostMapping("/login_do")
    @ResponseBody
    public Result<String> login_do(HttpServletResponse response, @Valid LoginVo loginVo){

        User user = userService.findByUsernameAndPassword(loginVo.getUsername(), MD5Utils.transferInputToDb(loginVo.getPassword()));
        if (user != null){
            /*生成分布式session，用cookie实现*/
            String token = UUIDUtils.uuid();
            /*把user加入到cookie和redis中*/
            userService.addCookie(response,user,token);
            return Result.success(user.getUsername()+user.getPassword());
        }
        return Result.error(CodeMsg.LOGIN_ERROR);
    }
}
