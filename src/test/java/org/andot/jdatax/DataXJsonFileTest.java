package org.andot.jdatax;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.junit.Test;

import org.andot.jdatax.entity.DBInfo;
import org.andot.jdatax.service.DataXJsonFileService;
import org.andot.jdatax.utils.JavaShellUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class DataXJsonFileTest extends BaseTest {
	
	@Resource
	private DataXJsonFileService dataXJsonFileService;
	@Autowired
	private JavaShellUtil javaShellUtil;
	
	@Test
	public void createDataXJsonFile() {
		DBInfo dbInfo = new DBInfo();
		dbInfo.setDbName("DynamicDatabase");
		dbInfo.setDbUser("bj");
		dbInfo.setDbPwd("Zd666666");
		dbInfo.setDbClass("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dbInfo.setDbUrl("jdbc:sqlserver://192.168.10.215:1433;DatabaseName=DynamicDatabase;");
		try {
			dataXJsonFileService.CreateDataXJsonFile(dbInfo, "dataxJsonTemp.json", "datax/job");
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void dataxStart() {
		dataXJsonFileService.dataxStart("python", "datax/bin/datax", "D:\\Upload\\datax");
		String cmds[] = {"python", "datax/bin/datax", "D:\\Upload\\datax\\SYS_CONFIGDataX.json"};
		try {
			JavaShellUtil.exec(cmds);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void consolePrint() throws IOException {
		javaShellUtil.execDi(new String[]{});
	}
}
