package com.example.seckill.vo;

import java.io.Serializable;
import java.util.Date;

import com.example.seckill.domain.Good;

public class GoodVo extends Good implements Serializable {

	private final static long serialVersionUID = 1L;
	private Double priceSeckill;
	private Integer stockSeckill;
	private Date startDate;
	private Date endDate;

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

	public Double getPriceSeckill() {
		return priceSeckill;
	}

	public void setPriceSeckill(Double priceSeckill) {
		this.priceSeckill = priceSeckill;
	}
}
