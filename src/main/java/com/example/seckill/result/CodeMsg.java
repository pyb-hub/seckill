package com.example.seckill.result;

/*枚举类，封装失败返回的信息*/
public class CodeMsg {

    private int code;
    private String msg;

    /*定义通用信息异常模块*/
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100,"服务端异常");
    public static  CodeMsg ACCESS_LIMIT = new CodeMsg(500101,"访问太频繁");
    /*表单参数校验异常，%s为输入的参数，参数拼接，下面要定义传入参数的方法*/
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数异常：%s");
    /*登录模块信息异常：5002XX*/
    public static CodeMsg LOGIN_ERROR = new CodeMsg(500200,"账号或者密码错误");
    public static CodeMsg SESSION_ERROR = new CodeMsg(500201,"账号过期，重新登录");
    /*商品模块信息异常：5003XX*/
    public static CodeMsg GOOD_ERROR = new CodeMsg(500300,"商品异常");
    /*订单模块信息异常：5004XX*/
    public static CodeMsg ORDER_ERROR = new CodeMsg(500400,"订单异常");
    public static CodeMsg ORDER_NOT_EXIST = new CodeMsg(500401,"订单不存在");
    /*秒杀模块信息异常：5005XX*/
    public static CodeMsg SECKILL_FAIL = new CodeMsg(500500,"秒杀已结束,下次再来吧~");
    public static CodeMsg SECKILL_REPEATE = new CodeMsg(500501,"你已经秒杀过,下次再来吧~");
    public static CodeMsg SECKILL_SUC = new CodeMsg(500502,"恭喜你，秒杀成功！");
    public static CodeMsg PATH_ERROR = new CodeMsg(500503,"秒杀路径不正确");


    private CodeMsg( ) {
    }
    /*不能主动创建*/
    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    /*表单参数校验异常填入参数%s*/
    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    @Override
    public String toString() {
        return "CodeMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
