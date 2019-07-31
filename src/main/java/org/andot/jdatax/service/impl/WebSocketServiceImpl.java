package org.andot.jdatax.service.impl;

import org.andot.jdatax.service.WebSocketService;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;

@Service("webSocketService")
public class WebSocketServiceImpl implements WebSocketService {

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 实现服务器主动推送
     */
    @Override
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
}
