package org.andot.jdatax.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.andot.jdatax.entity.DBInfo;
import org.andot.jdatax.entity.FieldDetailInfo;
import org.andot.jdatax.service.DataXJsonFileService;
import org.andot.jdatax.utils.DataBaseOperation;
import org.andot.jdatax.utils.FileOperateUtil;
import org.andot.jdatax.utils.JavaShellUtil;

@Service("dataXJsonFileService")
public class DataXJsonFileServiceImpl implements DataXJsonFileService {
	
	private Logger log = LoggerFactory.getLogger(DataXJsonFileServiceImpl.class);

	@Override
	public String CreateDataXJsonFile(DBInfo dbInfo, String tempFilePath, String filePath) throws ServletException, IOException {
		String resultStr = "";
		List<Map<String,String>> tableList = DataBaseOperation.getDatabaseTables(dbInfo);
		for (Map<String, String> map : tableList) {
			String tableName = map.get("name");
			if(tableName.equals("USER_INFO") || tableName.equals("DICAREA"))
				continue;
			List<FieldDetailInfo> listCol = DataBaseOperation.getTableColumDetail(tableName, dbInfo);
			String fields = "";
			for (FieldDetailInfo fieldDetailInfo : listCol) {
				fields += "\""+fieldDetailInfo.getTableCloumn()+"\", ";
			}
			if(fields.equals(""))
				continue;
			fields = fields.substring(0, fields.length()-2);
			try {
				String allstr = FileOperateUtil.readFileByName(tempFilePath);
				
				allstr = allstr.replace("{{fieldName}}", fields.toUpperCase());
				allstr = allstr.replace("{{oldTableName}}", tableName.toUpperCase());
				allstr = allstr.replace("{{newTableName}}", tableName.toLowerCase());
				
				FileOperateUtil.textWriteFile(allstr, filePath+"/" + tableName + "DataX.json");
				log.info(tableName+".json 文件写入成功！保存路径："+filePath +"; ");
				resultStr += tableName+".json 文件写入成功！保存路径："+filePath +"; ";
			} catch (Exception e) {
				log.error(tableName+".json 文件写入出错！出错原因："+e.getMessage() +"; ");
				resultStr += tableName+".json 文件写入出错！出错原因："+e.getMessage() +"; ";
				break;
			}
		}
		return resultStr;
	}

	@Override
	public String dataxStart(String pyPath, String dataxPath, String jsonPath) {
		String result = "同步成功";
		try {
			// 获得指定文件对象  
	        File file = new File(jsonPath);   
	        // 获得该文件夹内的所有文件   
	        File[] array = file.listFiles(); 
	        for(int i=0;i<array.length;i++) {   
	            if(array[i].isFile()) {
	            	System.err.println(array[i].getPath());
	            	String cmds[] = {pyPath, dataxPath, array[i].getPath()};
	        		JavaShellUtil.exec(cmds);
	        		Thread.sleep(8000);
	            }
	        }
		} catch (Exception e) {
			result = e.getMessage();
		}
		
		return result;
	}

	@Override
	public boolean isDataxStart(String jsonPath) {
		// 获得指定文件对象  
        File file = new File(jsonPath);   
        // 获得该文件夹内的所有文件   
        File[] array = file.listFiles();
        if(array.length > 0)
        	return true;
        else
        	return false;
	}

}
