package com.example.seckill.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/*login的参数封装类，加入验证*/
public class LoginVo {
    /*@NotNull：主要用在基本数据类型上(Int，Integer，Double)

     @NotBlank:主要用在String字符串上面(String)

     @NotEmpty; 加了@NotEmpty注解的String类 ，Collection集合，Map ，数组，这些是不能为null或者长度为0的;(String ,Collection,Map的isEmpty()方法)
    */
    @NotEmpty(message="username不能为空")
    private String username;
    @NotEmpty(message="密码不能为空")
    @Length(min = 6)
    private String password;
    @NotEmpty(message="电话不能为空")
    private String mobile;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "LoginVo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", mobile=" + mobile +
                '}';
    }
}
