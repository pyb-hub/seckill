package com.example.seckill.service.impl;

import java.util.Date;

import com.example.seckill.dao.OrderDao;
import com.example.seckill.domain.Order;
import com.example.seckill.domain.OrderSeckill;
import com.example.seckill.domain.User;
import com.example.seckill.vo.GoodVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class OrderService {
	
	@Autowired
	private OrderDao orderDao;
	
	public OrderSeckill getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
		return orderDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
	}

	@Transactional
	public Order createOrder(User user, GoodVo goods) {
		Order orderInfo = new Order();
		orderInfo.setCreateDate(new Date());
		orderInfo.setDeliveryAddrId(1L);
		orderInfo.setGoodCount(1);
		orderInfo.setGoodId(goods.getId());
		orderInfo.setGoodName(goods.getName());
		orderInfo.setGoodPrice(goods.getPriceSeckill());
		orderInfo.setStatus(0);
		orderInfo.setUserId(user.getId());

		orderDao.insert(orderInfo);

		OrderSeckill miaoshaOrder = new OrderSeckill();
		miaoshaOrder.setGoodId(goods.getId());
		miaoshaOrder.setOrderId(orderInfo.getId());
		miaoshaOrder.setUserId(user.getId());

		orderDao.insertMiaoshaOrder(miaoshaOrder);
		return orderInfo;
	}


	public Order getOrderById(long orderId) {
		return orderDao.getOrderById(orderId);
	}
	
}
