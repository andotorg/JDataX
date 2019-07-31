package org.andot.jdatax.utils;

import org.andot.jdatax.service.WebSocketService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class JavaShellUtil {

    @Resource
    private WebSocketService webSocketService;
	
	/**
	 * 定义传入shell脚本的参数，将参数放入字符串数组里  
	 * 
	 * 
	 * */
	public static String exec(String cmds[]) throws IOException{
        // 执行shell脚本  
        Process pcs = Runtime.getRuntime().exec("ping www.baidu.com");
        // 定义shell返回值  
        String result = null;  
        // 获取shell返回流  
        InputStreamReader in = new InputStreamReader(pcs.getInputStream());
        // 字符流转换字节流  
        BufferedReader br = new BufferedReader(in);
        // 这里也可以输出文本日志  
  
        String lineStr;
        while ((lineStr = br.readLine()) != null) {  
            result += lineStr;

            System.out.println(lineStr);
        }  
        // 关闭输入流  
        br.close();  
        in.close();  
        System.out.println("==============================" + result);  
        return result;
	}

    /**
     * 定义传入shell脚本的参数，将参数放入字符串数组里
     *
     *
     * */
    public void exec1(String cmds[]) throws IOException{
        // 执行shell脚本
        Process pcs = Runtime.getRuntime().exec("ping www.baidu.com");
        // 获取shell返回流
        InputStreamReader in = new InputStreamReader(pcs.getInputStream());
        // 字符流转换字节流
        BufferedReader br = new BufferedReader(in);
        // 这里也可以输出文本日志

        String lineStr;
        while ((lineStr = br.readLine()) != null) {
            webSocketService.sendMessage(lineStr);
            System.out.println(lineStr);
        }
        // 关闭输入流
        br.close();
        in.close();
    }
}
