package com.example.seckill.domain;

public class OrderSeckill {
	private Long id;
	private Long userId;
	private Long orderId;
	private Long goodId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getGoodId() {
		return goodId;
	}

	public void setGoodId(Long goodId) {
		this.goodId = goodId;
	}

	@Override
	public String toString() {
		return "OrderSeckill{" +
				"id=" + id +
				", userId=" + userId +
				", orderId=" + orderId +
				", goodId=" + goodId +
				'}';
	}
}
