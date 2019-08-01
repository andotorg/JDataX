package org.andot.jdatax.service;

import java.io.IOException;

public interface WebSocketService {


    void sendMessage(String message) throws IOException;
}
