package org.andot.jdatax;

import org.andot.jdatax.utils.JavaShellUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
//        long a1 = System.currentTimeMillis();
//        List<String> list = new ArrayList<>();
//        for (int i=0; i<99999; i++){
//            list.add(i+"");
//        }
//        long a2 = System.currentTimeMillis();
//        System.err.println("for====>"+(a2-a1)+"ms");
//
//        long b1 = System.currentTimeMillis();
//        List<String> list1 = new ArrayList<>();
//        list.forEach(item->{
//            String a = item;
//        });
//        long b2 = System.currentTimeMillis();
//        System.err.println("stream====>"+(b2-b1)+"ms");
        JavaShellUtil javaShellUtil = new JavaShellUtil();

        javaShellUtil.execDi(new String[]{"ping baidu.com"});
    }
}
