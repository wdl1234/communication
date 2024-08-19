package com.xt.communication.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author dlwu4
 * @version V1.0
 * @className WebSocketServerTwo
 * @description 测试webSocket session的高级使用之广播消息
 * @date 2024/08/14 10:57
 **/
@Slf4j
@Component
@ServerEndpoint("/webSocketTwo")
public class WebSocketServerTwo {

    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

    @PostConstruct
    public void init() {
        log.info("WebSocketServerTwo init");
    }

    /**
     * 建立连接
     *
     * @param session
     * @throws IOException
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {
        sessions.add(session);
        session.getBasicRemote().sendText("webSocket session open");
    }

    /**
     * 发送消息
     *
     * @param message
     * @param session
     * @throws IOException
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        // 对所有已建立的session发送消息
        for (Session sess : sessions) {
            if (sess.isOpen()) {
                sess.getBasicRemote().sendText("server response:" + message);
            }
        }
    }


    /**
     * 报错
     *
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("websocket error,{}", throwable.getMessage());
    }

    /**
     * 关闭连接
     *
     * @param session
     * @param reason
     * @throws IOException
     */
    @OnClose
    public void onClose(Session session, CloseReason reason) throws IOException {
        sessions.remove(session);
        session.close();
        log.info("webSocket session close");
    }

}
