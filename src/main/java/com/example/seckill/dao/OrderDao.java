package com.example.seckill.dao;

import com.example.seckill.domain.Order;
import com.example.seckill.domain.OrderSeckill;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface OrderDao {
	
	@Select("select * from t_order_seckill where user_id=#{userId} and good_id=#{goodId}")
	public OrderSeckill getMiaoshaOrderByUserIdGoodsId(@Param("userId") long userId, @Param("goodId") long goodsId);

	@Insert("insert into t_order(user_id, good_id, good_name, good_count, good_price, status, create_date, delivery_addr_id)values("
			+ "#{userId}, #{goodId}, #{goodName}, #{goodCount}, #{goodPrice}, #{status},#{createDate},#{deliveryAddrId} )")
	@SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
	public long insert(Order order);
	
	@Insert("insert into t_order_seckill (user_id, good_id, order_id)values(#{userId}, #{goodId}, #{orderId})")
	public int insertMiaoshaOrder(OrderSeckill Order);

	@Delete("delete from t_order_seckill where order_id = #{orderId}")
	public void deleteSecOrder(@Param("orderId") long orderId);

	@Select("select * from t_order where id = #{orderId}")
	public Order getOrderById(@Param("orderId")long orderId);
	
}
