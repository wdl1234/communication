package com.xt.communication.sse.exception;

import lombok.Data;

/**
 * @author dlwu4
 * @version V1.0
 * @className BaseException
 * @description 自定义异常处理
 * @date 2023/07/27 16:41
 **/
@Data
public class BaseException extends RuntimeException {

    private Integer code;
    private String message;

    public BaseException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseException(String message) {
        this.message = message;
    }


}