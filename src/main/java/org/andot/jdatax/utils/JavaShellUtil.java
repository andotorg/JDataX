package org.andot.jdatax.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JavaShellUtil {
	
	/**
	 * 定义传入shell脚本的参数，将参数放入字符串数组里  
	 * 
	 * 
	 * */
	public static String exec(String cmds[]) throws IOException{
        // 执行shell脚本  
        Process pcs = Runtime.getRuntime().exec(cmds);  
        // 定义shell返回值  
        String result = null;  
        // 获取shell返回流  
        BufferedInputStream in = new BufferedInputStream(pcs.getInputStream());  
        // 字符流转换字节流  
        BufferedReader br = new BufferedReader(new InputStreamReader(in));  
        // 这里也可以输出文本日志  
  
        String lineStr;  
        while ((lineStr = br.readLine()) != null) {  
            result += lineStr;  
        }  
        // 关闭输入流  
        br.close();  
        in.close();  
        System.out.println("==============================" + result);  
        return result;
	}
}
