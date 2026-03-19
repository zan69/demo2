package com.example.demo.common;

/**
 * 无Lombok版本：统一响应封装
 */
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    private Result() {}

    // 手动添加getter/setter
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // 以下静态工厂方法和之前一致
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(ResultCode resultCode) {
        Result<T> result = new Result<>();
        result.setCode(resultCode.getCode());
        result.setMsg(resultCode.getMsg());
        return result;
    }

    public static <T> Result<T> error(ResultCode resultCode, String customMsg) {
        Result<T> result = new Result<>();
        result.setCode(resultCode.getCode());
        result.setMsg(customMsg);
        return result;
    }
}