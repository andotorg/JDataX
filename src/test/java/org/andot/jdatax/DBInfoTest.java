package org.andot.jdatax;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import org.andot.jdatax.entity.DBInfo;
import org.andot.jdatax.service.DBInfoService;
import org.andot.jdatax.service.DataTransferService;
import org.andot.jdatax.utils.DataBaseOperation;
import org.andot.jdatax.vo.ResultJson;

public class DBInfoTest extends BaseTest {
	@Resource
	private DBInfoService dbInfoService;
	@Resource
	private DataTransferService dataTransferService;
	
	@Test
	public void addDBInfo() {
		DBInfo dbInfo = new DBInfo();
		dbInfo.setDbName("mysql");
		dbInfo.setDbUser("root");
		dbInfo.setDbPwd("iesapp");
		System.err.println(dbInfoService.addDBInfo(dbInfo));
	}
	
	@Test
	public void getData() throws Exception{
		DBInfo dbInfo = new DBInfo();
		dbInfo.setDbName("test");
		dbInfo.setDbUser("bj");
		dbInfo.setDbPwd("Zd666666");
		dbInfo.setDbClass("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dbInfo.setDbUrl("jdbc:sqlserver://192.168.10.215:1433;DatabaseName=DynamicDatabase;");
		System.err.println(ResultJson.getJsonMsg(new ResultJson(1, DataBaseOperation.getTableData("CITY", "DICAREA", dbInfo), 0, "success")));
	}
	
	@Test
	public void getCount() throws Exception{
		DBInfo dbInfo = new DBInfo();
		dbInfo.setDbName("test");
		dbInfo.setDbUser("bj");
		dbInfo.setDbPwd("Zd666666");
		dbInfo.setDbClass("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dbInfo.setDbUrl("jdbc:sqlserver://192.168.10.215:1433;DatabaseName=DynamicDatabase;");
		System.err.println(ResultJson.getJsonMsg(new ResultJson(1, DataBaseOperation.getTableColumDetail("MINE_INFO", dbInfo), 0, "success")));
	}
	
	
	@Test
	public void dt() throws Exception{
		List<DBInfo> dblist = new ArrayList<DBInfo>();
		DBInfo dbInfo1 = new DBInfo();
		dbInfo1.setDbName("DynamicDatabase");
		dbInfo1.setDbUser("bj");
		dbInfo1.setDbPwd("Zd666666");
		dbInfo1.setDbClass("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dbInfo1.setDbUrl("jdbc:sqlserver://192.168.159.129:1433;DatabaseName=DynamicDatabase;");
		dblist.add(dbInfo1);
		DBInfo dbInfo2 = new DBInfo();
		dbInfo2.setDbName("mgeids");
		dbInfo2.setDbUser("root");
		dbInfo2.setDbPwd("andot");
		dbInfo2.setDbClass("com.mysql.jdbc.Driver");
		dbInfo2.setDbUrl("jdbc:mysql://127.0.0.1:3306/mgeids?useUnicode=true&characterEncoding=UTF-8");
		dblist.add(dbInfo2);
		List<String> tables = new ArrayList<String>();
		tables.add("ROLE");
		tables.add("MINE_SURVEY_INFO");
		System.err.println(ResultJson.getJsonMsg(new ResultJson(1, dataTransferService.dataTransfer(dblist, tables, null), 0, "success")));
	}
	
	
	@Test
	public void getIdMaxValue() {
		DBInfo dbInfo2 = new DBInfo();
		dbInfo2.setDbName("mgeids");
		dbInfo2.setDbUser("root");
		dbInfo2.setDbPwd("andot");
		dbInfo2.setDbClass("com.mysql.jdbc.Driver");
		dbInfo2.setDbUrl("jdbc:mysql://127.0.0.1:3306/mgeids?useUnicode=true&characterEncoding=UTF-8");
		List<Object> list = DataBaseOperation.getTableDataObject("mine_survey_info.MINE_SURVEY_ID", "mine_survey_info", dbInfo2);
		System.err.println(list.get(0));
	}
}
