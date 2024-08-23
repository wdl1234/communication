package com.xt.communication.sse.result;

/**
 * @author dlwu4
 * @version V1.0
 * @enumName ResultCode
 * @description 通用返回状态码枚举类
 * @date 2023/08/09 13:40
 **/
public enum ResultCode {

    /**
     * 通用返回码定义
     */
    SUCCESS(200, "操作成功"),
    FAILURE(500, "系统错误"),
    BAD_REQUEST(400, "错误的请求");

    private int code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}