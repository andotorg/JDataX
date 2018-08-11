package org.andot.jdatax.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.andot.jdatax.entity.DBInfo;
import org.andot.jdatax.service.DataXJsonFileService;
import org.andot.jdatax.utils.FileToZip;
import org.andot.jdatax.vo.ResultJson;

@RestController
public class DataXJsonFileController {
	
	@Resource
	private DataXJsonFileService dataXJsonFileService;
	
	/**
	 * 批量自动生成datax所需要的json配置文件
	 * 
	 * */
	@GetMapping("/createDataXJsonFile")
	public String createDataXJsonFile(DBInfo dbInfo, String filePath, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
		String tempFilePath = "dataxJsonTemp.json";
		String msg = dataXJsonFileService.CreateDataXJsonFile(dbInfo, tempFilePath, filePath);
		String result = "";
		//压缩
		try {
			String fileName = "datax-json";
			FileToZip.fileToZip(filePath , filePath.substring(0, filePath.lastIndexOf("\\")), fileName);
			result = filePath.substring(0, filePath.lastIndexOf("\\"))+"\\"+fileName+".zip";
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return ResultJson.getJsonMsg(new ResultJson(1, result, 0, msg));
	}
	
	@GetMapping("/isDataxJson")
	public String isDataxJson(String jsonPath){
		boolean result = dataXJsonFileService.isDataxStart(jsonPath);
		return ResultJson.getJsonMsg(new ResultJson(1, result, 0, ""));
	}
	
	@GetMapping("/dataxStart")
	public String dataxStart(String pyPath, String dataxPath, String jsonPath){
		String result = dataXJsonFileService.dataxStart(pyPath, dataxPath, jsonPath);
		return ResultJson.getJsonMsg(new ResultJson(1, result, 0, ""));
	}

}
