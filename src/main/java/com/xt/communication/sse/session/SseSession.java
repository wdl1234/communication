package com.xt.communication.sse.session;

import com.xt.communication.sse.exception.BaseException;
import lombok.Data;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dlwu4
 * @version V1.0
 * @className SseSession
 * @description 储存用户连接进来的SseEmitter对象
 * @date 2024/08/23 10:22
 **/
@Data
public class SseSession {

    private static Map<String, SseEmitter> sessionMap = new ConcurrentHashMap<>();

    public static void add(String sessionKey, SseEmitter sseEmitter) {
        if (sessionMap.get(sessionKey) != null) {
            throw new BaseException("User exists!");
        }
        sessionMap.put(sessionKey, sseEmitter);
    }

    public static boolean exists(String sessionKey) {
        return sessionMap.get(sessionKey) != null;
    }

    public static boolean remove(String sessionKey) {
        SseEmitter sseEmitter = sessionMap.get(sessionKey);
        if (sseEmitter != null) {
            sseEmitter.complete();
        }
        return false;
    }

    public static void onError(String sessionKey, Throwable throwable) {
        SseEmitter sseEmitter = sessionMap.get(sessionKey);
        if (sseEmitter != null) {
            sseEmitter.completeWithError(throwable);
        }
    }

    public static void send(String sessionKey, String content) throws IOException {
        sessionMap.get(sessionKey).send(content);
    }
}
