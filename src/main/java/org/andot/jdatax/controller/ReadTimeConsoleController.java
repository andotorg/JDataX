package org.andot.jdatax.controller;


import com.alibaba.fastjson.JSONObject;
import org.andot.jdatax.utils.JavaShellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@RestController
@RequestMapping("/readTime")
public class ReadTimeConsoleController {

    @Autowired
    private JavaShellUtil javaShellUtil;

    @GetMapping("")
    public JSONObject getConsoleLog(){
        try {
            javaShellUtil.execDi(new String[]{"ping", "baidu.com"});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
