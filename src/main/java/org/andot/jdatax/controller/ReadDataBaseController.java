package org.andot.jdatax.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import org.andot.jdatax.entity.DBInfo;
import org.andot.jdatax.service.DBInfoService;
import org.andot.jdatax.utils.DataBaseOperation;
import org.andot.jdatax.vo.ResultJson;

/**
 * 显示数据库基本字段表
 * 
 * */
@RestController
@RequestMapping("/rest/jdatax/readDataBase")
public class ReadDataBaseController {
	
	@Resource
	private DBInfoService dbInfoService;
	
	@RequestMapping("")
	public ModelAndView index(){
		return new ModelAndView("");
	}
	
	@RequestMapping("/testConnection")
	public String testConnection(DBInfo dbInfo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn=null;
		try{
			Class.forName(dbInfo.getDbClass());
			conn=DriverManager.getConnection(dbInfo.getDbUrl(), dbInfo.getDbUser(), dbInfo.getDbPwd());
			return ResultJson.getJsonMsg(new ResultJson(1, "false", 0, "success"));
		}catch(Exception ex){
			return ResultJson.getJsonMsg(new ResultJson(1, "false", 0, "error; info:"+ex.getMessage()));
		}finally{
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 显示数据库中所有表格
	 * 
	 * */
	@RequestMapping("/showTables")
	public String showTables(DBInfo dbInfo, int flastRow, int pageSize, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
		dbInfoService.addDBInfo(dbInfo);
		List<Map<String,String>> list = new ArrayList<>();
		list = DataBaseOperation.getDatabaseTables(dbInfo);
		return ResultJson.getJsonMsg(new ResultJson(1, list, list.size(), "success"));
	}
	
	/**
	 * 显示数据库中指定表的所有字段
	 * 
	 * */
	@RequestMapping("/showColumn")
	public String showColumn(DBInfo dbInfo, String tableName, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Object> list = new ArrayList<>();
		try {
			list = DataBaseOperation.getTableColum(tableName, dbInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultJson.getJsonMsg(new ResultJson(1, list, list.size(), "success"));
	}
	
	@RequestMapping("/showTableData")
	public String showTableData(DBInfo dbInfo, String tableName, String fields, HttpServletRequest req, HttpServletResponse resp){
		List<Object> list = new ArrayList<>();
		try {
			list = DataBaseOperation.getTableDataObject(fields, tableName, dbInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultJson.getJsonMsg(new ResultJson(1, list, list.size(), "success"));
	}
}
