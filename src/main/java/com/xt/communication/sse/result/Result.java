package com.xt.communication.sse.result;

import lombok.Data;

/**
 * @author dlwu4
 * @version V1.0
 * @className Result
 * @description 返回结果类
 * @date 2023/08/09 10:53
 **/
@Data
public class Result<T> {
    /**
     * 状态码
     */
    private int code;

    /**
     * 返回的数据
     */
    private T data;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 有参构造
     *
     * @param code
     * @param message
     * @param data
     */
    public Result(int code, String message, T data) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * 无参构造
     */
    public Result() {
        this.code = ResultCode.SUCCESS.getCode();
        this.message = ResultCode.SUCCESS.getMessage();
    }


    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data) {
        return new Result(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 返回Result
     */
    public static <T> Result<T> success() {
        return new Result();
    }

    /**
     * 错误
     *
     * @param code
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(ResultCode code) {
        return new Result(code.getCode(), code.getMessage(), null);
    }

    /**
     * 错误
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(int code, String message) {
        return new Result(code, message, null);
    }


}