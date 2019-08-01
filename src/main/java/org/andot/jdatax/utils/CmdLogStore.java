package org.andot.jdatax.utils;

import org.andot.jdatax.controller.ReadTimeConsoleController;
import org.springframework.stereotype.Component;

import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class CmdLogStore {
    private String log;
    public static CopyOnWriteArraySet<ReadTimeConsoleController> webSocketSet = new CopyOnWriteArraySet<>();

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public static CopyOnWriteArraySet<ReadTimeConsoleController> getWebSocketSet() {
        return webSocketSet;
    }

    public static void setWebSocketSet(CopyOnWriteArraySet<ReadTimeConsoleController> webSocketSet) {
        CmdLogStore.webSocketSet = webSocketSet;
    }
}
