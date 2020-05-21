package com.example.seckill.controller;

import com.example.seckill.domain.Order;
import com.example.seckill.domain.User;
import com.example.seckill.redis.RedisService;
import com.example.seckill.result.CodeMsg;
import com.example.seckill.result.Result;
import com.example.seckill.service.impl.GoodService;
import com.example.seckill.service.impl.OrderService;
import com.example.seckill.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	UserService userService;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	GoodService goodService;
	
    @GetMapping("/detail/{orderId}")
    @ResponseBody
    public Result<Order> info(User user, @PathVariable("orderId") long orderId) {
    	if(user == null) {
    		return Result.error(CodeMsg.SESSION_ERROR);
    	}
    	Order order = orderService.getOrderById(orderId);
    	if(order == null) {
    		return Result.error(CodeMsg.ORDER_NOT_EXIST);
    	}
    	return Result.success(order);
    }
    
}
