package org.andot.jdatax.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

public class StringOutFile {
	public static void charOutStream(String path, String fileName, String context) throws Exception{
        // 1：利用File类找到要操作的对象
        File file = new File("../"+ path +"/"+ fileName +".java");
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        //2：准备输出流
        Writer out = new FileWriter(file);
        out.write(context);
        out.close();
    }
}
