package com.xt.communication.sse.controller;

import com.xt.communication.sse.param.SendMegRequestParam;
import com.xt.communication.sse.result.Result;
import com.xt.communication.sse.result.ResultCode;
import com.xt.communication.sse.service.SseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author dlwu4
 * @version V1.0
 * @className SseController
 * @description sse
 * @date 2024/08/23 10:50
 **/
@Slf4j
@RestController
@RequestMapping(value = "/v1/sse")
@RequiredArgsConstructor
public class SseController {

    private final SseService sseService;

    /**
     * 订阅
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "/subscribe/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable("userId") String userId) {
        // 订阅
        return sseService.connect(userId);
    }

    /**
     * 发送消息
     *
     * @param sendMegRequestParam
     * @return
     */
    @PostMapping(value = "/send")
    public Result<Void> sendMessage(@RequestBody SendMegRequestParam sendMegRequestParam) {
        // 发送消息
        if (sseService.send(sendMegRequestParam.getUserId(), sendMegRequestParam.getMsg())) {
            return Result.success();
        }
        return Result.error(500, "发送消息失败");
    }

    /**
     * 关闭
     *
     * @param userId
     */
    @GetMapping(value = "/close/{userId}")
    public Result<Void> close(@PathVariable("userId") String userId) {
        // 关闭
        boolean close = sseService.close(userId);
        if (close) {
            return Result.success();
        }
        return Result.error(500, "关闭失败");
    }
}
