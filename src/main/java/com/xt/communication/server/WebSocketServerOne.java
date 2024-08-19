package com.xt.communication.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @author dlwu4
 * @version V1.0
 * @className WebSocketServerOne
 * @description 测试webSocket 一对一发送消息（通过sessionId)
 * @date 2024/08/14 09:32
 **/
@Slf4j
@Component
@ServerEndpoint("/webSocketOne")
public class WebSocketServerOne {

    @PostConstruct
    public void init() {
        log.info("WebSocketServerOne init");
    }

    /**
     * 建立连接
     *
     * @param session
     * @throws IOException
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {
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
        if (session.isOpen()) {
            // 发送消息，跟自己实际的业务挂钩
            session.getBasicRemote().sendText("server response:" + message);
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
        session.close();
        log.info("webSocket session close");
    }


}
