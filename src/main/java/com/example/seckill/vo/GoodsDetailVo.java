package com.example.seckill.vo;

import com.example.seckill.domain.User;


public class GoodsDetailVo{

    private GoodVo goodVo;
    private User user;
    private Integer status;
    private Integer remainSeconds;

    public GoodVo getGoodVo() {
        return goodVo;
    }

    public void setGoodVo(GoodVo goodVo) {
        this.goodVo = goodVo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(Integer remainSeconds) {
        this.remainSeconds = remainSeconds;
    }
}
