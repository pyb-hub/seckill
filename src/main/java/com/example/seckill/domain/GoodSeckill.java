package com.example.seckill.domain;

import java.util.Date;

public class GoodSeckill {
	private Long id;
	private Long goodId;
	private Double priceSeckill;
	private Integer stockSeckill;
	private Date startDate;
	private Date endDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGoodId() {
		return goodId;
	}

	public void setGoodId(Long goodId) {
		this.goodId = goodId;
	}

	public Double getPriceSeckill() {
		return priceSeckill;
	}

	public void setPriceSeckill(Double priceSeckill) {
		this.priceSeckill = priceSeckill;
	}

	public Integer getStockSeckill() {
		return stockSeckill;
	}

	public void setStockSeckill(Integer stockSeckill) {
		this.stockSeckill = stockSeckill;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "GoodSeckill{" +
				"id=" + id +
				", goodId=" + goodId +
				", priceSeckill=" + priceSeckill +
				", stockSeckill=" + stockSeckill +
				", startDate=" + startDate +
				", endDate=" + endDate +
				'}';
	}
}
