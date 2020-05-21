package com.example.seckill.dao;

import java.util.List;

import com.example.seckill.domain.GoodSeckill;
import com.example.seckill.vo.GoodVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface GoodDao {
	
	@Select("select g.*,mg.stock_seckill, mg.start_date, mg.end_date,mg.price_seckill from t_good_seckill mg inner join t_good g on mg.good_id = g.id")
	public List<GoodVo> listGoodsVo();

	@Select("select * from t_good_seckill mg inner join t_good g on mg.good_id = g.id where g.id = #{goodId}")
	public GoodVo getGoodsVoByGoodsId(@Param("goodId") long goodId);

	@Update("update t_good_seckill set stock_seckill = stock_seckill - 1 where good_id = #{goodId} and stock_seckill > 0")
	public int reduceStock(GoodSeckill g);
	
}
