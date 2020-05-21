package com.example.seckill.result;

/*封装返回的结果集*/
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    /**
     * 成功时候的调用
     * */
    public static <T> Result<T> success(T data){
        return new Result<>(data);
    }

    /**
     * 失败时候的调用
     * */
    public static <T> Result<T> error(CodeMsg cm){
        return new  Result<>(cm);
    }

    /*成功的构造函数，只要传输成功后的data就行，其他二个参数一致不用传递*/
    private Result(T data) {
        this.data = data;
        code=0;
        msg="success!";
    }
    /*失败后返回的构造函数：需要传递失败的code和失败的msg，data不用传递*/
    private Result(CodeMsg cm) {
        if (cm == null){
            return;
        }
        code=cm.getCode();
        msg=cm.getMsg();
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
