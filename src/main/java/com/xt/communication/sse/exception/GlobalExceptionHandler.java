package com.xt.communication.sse.exception;


import com.xt.communication.sse.result.Result;
import com.xt.communication.sse.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dlwu4
 * @version V1.0
 * @className GlobalExceptionHandler
 * @description 全局异常处理
 * @date 2023/07/27 16:41
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * post请求参数校验异常
     *
     * @param e
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        String message = result.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("；"));
        log.error("methodArgumentNotValidException message is :{}", message);
        return Result.error(ResultCode.BAD_REQUEST.getCode(), message);
    }

    /**
     * get请求校验参数异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(BindException.class)
    public Result handleValidation(BindException exception) {
        BindingResult result = exception.getBindingResult();
        String message = result.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("；"));
        log.error("bindException message is :{}", message);
        return Result.error(ResultCode.BAD_REQUEST.getCode(), message);
    }

    /**
     * 请求校验参数异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result handleConstraintViolationException(ConstraintViolationException exception) {
        List<String> msgList = new ArrayList<>();
        for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
            msgList.add(constraintViolation.getMessage());
        }
        String message = msgList.stream().collect(Collectors.joining("；"));
        log.error("constraintViolationException message is :{}", message);
        return Result.error(ResultCode.BAD_REQUEST.getCode(), message);
    }


    /**
     * 自定义异常捕获拦截
     *
     * @param baseException
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public Result handlerBaseException(BaseException baseException) {
        log.error("baseException message is :{}", baseException.getMessage());
        if (baseException.getCode() == null) {
            baseException.setCode(ResultCode.FAILURE.getCode());
        }
        return Result.error(baseException.getCode(), baseException.getMessage());
    }

    /**
     * 拦截文件上传异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(MultipartException.class)
    public Result handlerMultipartException(MultipartException exception) {
        log.error("exception message is :{}", exception);
        return Result.error(ResultCode.FAILURE.getCode(), "上传文件错误");
    }

    /**
     * 全局异常拦截
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception exception) {
        log.error("exception message is :{}", exception);
        return Result.error(ResultCode.FAILURE.getCode(), exception.getMessage());
    }
}
