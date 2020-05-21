package com.example.seckill.service.impl;



import com.example.seckill.dao.GoodDao;
import com.example.seckill.domain.GoodSeckill;
import com.example.seckill.vo.GoodVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GoodService {
	
	@Autowired
	private GoodDao goodDao;
	
	public List<GoodVo> listGoodsVo(){
		return goodDao.listGoodsVo();
	}

	public GoodVo getGoodsVoByGoodsId(Long goodId) {
		return goodDao.getGoodsVoByGoodsId(goodId);
	}

	public int reduceStock(GoodVo good) {
		GoodSeckill g = new GoodSeckill();
		g.setGoodId(good.getId());
		return goodDao.reduceStock(g);
	}
	
	
	
}
