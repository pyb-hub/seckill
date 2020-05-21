package com.example.seckill.vo;

import com.example.seckill.domain.User;

import java.io.Serializable;

public class MQMsgVo implements Serializable {

    private final static long serialVersionUID = 1L;
    private User user;
    private Long GoodId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getGoodId() {
        return GoodId;
    }

    public void setGoodId(Long goodId) {
        GoodId = goodId;
    }
}
