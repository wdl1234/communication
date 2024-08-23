package com.xt.communication.sse.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author dlwu4
 * @version V1.0
 * @className SseService
 * @description sse服务接口
 * @date 2024/08/23 10:27
 **/
public interface SseService {

    /**
     * 连接
     *
     * @param userId
     * @return
     */
    SseEmitter connect(String userId);

    /**
     * 发送
     *
     * @param userId
     * @param content
     * @return
     */
    boolean send(String userId, String content);

    /**
     * 关闭
     *
     * @param userId
     * @return
     */
    boolean close(String userId);

}
