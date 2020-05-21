package com.example.seckill.dao;

import com.example.seckill.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository/*接口可不加这个注入注解，加了mapper就自动jdk代理生成实现类*/
public interface UserDao {


    @Select("select * from t_user where id = #{id}")
    User findById(Long id);
    @Select("select * from t_user where username = #{username}")
    User findByUsername(String username);
    @Select("select * from t_user where username = #{username} and password = #{password}")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password")String password);
}
