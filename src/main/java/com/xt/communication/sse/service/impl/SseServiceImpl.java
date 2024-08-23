package com.xt.communication.sse.service.impl;

import com.xt.communication.sse.exception.BaseException;
import com.xt.communication.sse.service.SseService;
import com.xt.communication.sse.session.SseSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * @author dlwu4
 * @version V1.0
 * @className SseServerImpl
 * @description sse服务实现类
 * @date 2024/08/23 10:29
 **/
@Slf4j
@Service
public class SseServiceImpl implements SseService {

    /**
     * 订阅
     *
     * @param userId
     * @return
     */
    @Override
    public SseEmitter connect(String userId) {
        if (SseSession.exists(userId)) {
            SseSession.remove(userId);
        }
        SseEmitter sseEmitter = new SseEmitter(0L);
        sseEmitter.onError((err) -> {
            log.error("type: SseSession Error, msg: {} session Id : {}", err.getMessage(), userId);
            SseSession.onError(userId, err);
        });

        sseEmitter.onTimeout(() -> {
            log.info("type: SseSession Timeout, session Id : {}", userId);
            SseSession.remove(userId);
        });

        sseEmitter.onCompletion(() -> {
            log.info("type: SseSession Completion, session Id : {}", userId);
            SseSession.remove(userId);
        });
        SseSession.add(userId, sseEmitter);
        return sseEmitter;
    }

    /**
     * 发送消息
     *
     * @param userId
     * @param content
     * @return
     */
    @Override
    public boolean send(String userId, String content) {
        if (SseSession.exists(userId)) {
            try {
                SseSession.send(userId, content);
                return true;
            } catch (IOException exception) {
                log.error("type: SseSession send error:IOException, msg: {} session Id : {}", exception.getMessage(), userId);
            }
        } else {
            throw new BaseException("User Id " + userId + " not Found");
        }
        return false;
    }


    /**
     * 关闭连接
     *
     * @param userId
     * @return
     */
    @Override
    public boolean close(String userId) {
        log.info("type: SseSession Close, session Id : {}", userId);
        return SseSession.remove(userId);
    }
}
