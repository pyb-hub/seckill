package com.example.seckill.controller;

import com.alibaba.druid.support.json.JSONUtils;
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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    /*跳转到login界面*/
    @RequestMapping("/register")
    public String login(){
        return "register";
    }

    /*login提交的处理*/
    @PostMapping("/register_do")
    @ResponseBody
    public Result<String> login_do(HttpServletResponse response, @Valid LoginVo loginVo){


        return Result.error(CodeMsg.LOGIN_ERROR);
    }
}
