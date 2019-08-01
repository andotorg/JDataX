package org.andot.jdatax.service.impl;

import org.andot.jdatax.service.WebSocketService;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/***
 * websocket
 */
@ServerEndpoint("/webSocket")
@Service("webSocketService")
public class WebSocketServiceImpl implements WebSocketService {

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    public static ConcurrentHashMap<String, Session> webSocketSet = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.put("union", session);
    }

    @OnClose
    public void onClose(){
        webSocketSet.remove("union");
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端" + session.getId() + "的消息:" + message);
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.err.println(session.getId() + "发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    @Override
    public void sendMessage(String message) throws IOException {
        webSocketSet.get("union").getBasicRemote().sendText(message);
    }
}
