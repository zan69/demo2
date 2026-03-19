/**
 * 自定义业务状态码枚举
 */
package com.example.demo.common;
public enum ResultCode {
    // 通用状态码
    SUCCESS(200, "操作成功"),
    SYSTEM_ERROR(500, "系统异常，请稍后重试"),
    TOKEN_INVALID(401, "Token无效或未登录"),
    PARAM_ERROR(400, "参数格式错误");

    // 状态码
    private final int code;
    // 提示信息
    private final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // getter方法
    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}