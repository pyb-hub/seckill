package com.example.seckill.domain;

import java.util.Date;

public class Order {
	private Long id;
	private Long userId;
	private Long goodId;
	private Long  deliveryAddrId;
	private String goodName;
	private Integer goodCount;
	private Double goodPrice;
	private Integer status;
	private Date createDate;
	private Date payDate;

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

	public Long getGoodId() {
		return goodId;
	}

	public void setGoodId(Long goodId) {
		this.goodId = goodId;
	}

	public Long getDeliveryAddrId() {
		return deliveryAddrId;
	}

	public void setDeliveryAddrId(Long deliveryAddrId) {
		this.deliveryAddrId = deliveryAddrId;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public Integer getGoodCount() {
		return goodCount;
	}

	public void setGoodCount(Integer goodCount) {
		this.goodCount = goodCount;
	}

	public Double getGoodPrice() {
		return goodPrice;
	}

	public void setGoodPrice(Double goodPrice) {
		this.goodPrice = goodPrice;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	@Override
	public String toString() {
		return "Order{" +
				"id=" + id +
				", userId=" + userId +
				", goodId=" + goodId +
				", deliveryAddrId=" + deliveryAddrId +
				", goodName='" + goodName + '\'' +
				", goodCount=" + goodCount +
				", goodPrice=" + goodPrice +
				", status=" + status +
				", createDate=" + createDate +
				", payDate=" + payDate +
				'}';
	}
}
