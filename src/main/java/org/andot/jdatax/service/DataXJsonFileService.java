package org.andot.jdatax.service;

import java.io.IOException;

import javax.servlet.ServletException;

import org.andot.jdatax.entity.DBInfo;

public interface DataXJsonFileService {
	
	/**
	 * 传入表名和字段名生成datax需要用的json配置文件
	 * 
	 * */
	String CreateDataXJsonFile(DBInfo dbInfo, String tempFilePath, String filePath) throws ServletException, IOException;
	
	String dataxStart(String pyPath, String dataxPath, String jsonPath);
	
	boolean isDataxStart(String jsonPath);
}
