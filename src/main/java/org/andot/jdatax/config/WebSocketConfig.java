package org.andot.jdatax.config;

import org.andot.jdatax.controller.ReadTimeConsoleController;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.io.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 开启webscket支持
 *
 */
@Configuration
public class WebSocketConfig {

    // 用来记录当前在线连接数
    private static volatile int onlineCount = 0;

    // 标记文件已读行数
    private static volatile long lines = 0L;

    // 判断是否是第一次加载
    private static volatile boolean isFirstRunning = true;

    // concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象
    // 若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    public static CopyOnWriteArraySet<ReadTimeConsoleController> webSocketSet = new CopyOnWriteArraySet<>();

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

    // 开启监听
    public static synchronized void startListening(String rootDir) {
        if (!isFirstRunning) {
            System.err.println("监听服务已经启动!");
            return;
        }

    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        onlineCount--;
    }

}
