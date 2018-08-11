package org.andot.jdatax.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import org.andot.jdatax.entity.DBInfo;
import org.andot.jdatax.entity.FieldDetailInfo;

/**
 * 数据库操作工具类
 * 
 * */
public class DataBaseOperation {
	
	/**
	 * 获取数据库的连接
	 * 
	 * 
	 * */
	private static Connection getConnection(DBInfo dbInfo) throws Exception{
		Connection conn = null;
		String username=dbInfo.getDbUser();
		String password=dbInfo.getDbPwd();
		String driver=dbInfo.getDbClass();
		String url=dbInfo.getDbUrl();
		Class.forName(driver);
		conn=DriverManager.getConnection(url, username, password);
		return conn;
	}
	
	/**
	 * 获取数据库中所有的表
	 * @throws SQLException */
	public static List<Map<String,String>> getDatabaseTables(DBInfo dbInfo) throws ServletException, IOException {
		Connection conn=null;
		ResultSet rs = null;
		List<Map<String,String>> tables = new ArrayList<Map<String,String>>();
		try{
			conn=getConnection(dbInfo);
			DatabaseMetaData metaData = conn.getMetaData();
			String url = metaData.getURL();
			String schema = null;
			if (url.toLowerCase().contains("oracle")) {
				schema = metaData.getUserName();
			}
			rs = metaData.getTables(null, schema, "%", new String[] { "TABLE","VIEW" });
			while (rs.next()) {
				Map<String,String> table = new HashMap<String,String>();
				table.put("name",rs.getString("TABLE_NAME"));
				table.put("type",rs.getString("TABLE_TYPE"));
				tables.add(table);
			}
		}catch(Exception ex){
			System.err.println(ex.getMessage());
		}finally{
			try {
				rs.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tables;
	}
	
	/**
	 * 获取数据库中所有的表个数 普通写法
	 * 
	 * */
	public static boolean getTableCount(String tableName, DBInfo dbInfo) {
		Connection conn=null;
		PreparedStatement ps = null;
		Integer count = 0;
		try{
			conn=getConnection(dbInfo);
			DataSource dataSource=new SingleConnectionDataSource(conn,false);
			StringBuffer sql = new StringBuffer("select count(table_name) as tablecount from information_schema.tables where table_name = '"+tableName+"' AND TABLE_SCHEMA = '"+dbInfo.getDbName()+"'");
			ps = dataSource.getConnection().prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				count = rs.getInt("tablecount");
			}
		}catch(Exception ex){
			System.err.println(ex.getMessage());
		}finally{
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(count == null || count == 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取数据库中指定表的所有字段工具类
	 * 
	 * */
	public static List<Object> getTableColum(String tableName, DBInfo dbInfo) {
		Connection conn=null;
		List<Object> fields = new ArrayList<Object>();
		try{
			conn=getConnection(dbInfo);
			DataSource dataSource=new SingleConnectionDataSource(conn,false);
			NamedParameterJdbcTemplate jdbc=new NamedParameterJdbcTemplate(dataSource);
			StringBuffer sql = new StringBuffer("select COLUMN_NAME from information_schema.columns where TABLE_NAME= '"+tableName+"' GROUP BY COLUMN_NAME");
			
			fields = jdbc.getJdbcOperations().queryForList(sql.toString(), Object.class);
		}catch(Exception ex){
			System.err.println(ex.getMessage());
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fields;
	}
	
	/**
	 * 获取数据库中指定表的所有字段工具类 详细信息
	 * 
	 * */
	public static List<FieldDetailInfo> getTableColumDetail(String tableName, DBInfo dbInfo) {
		Connection conn=null;
		List<FieldDetailInfo> dataInfo = new ArrayList<FieldDetailInfo>();
		try{
			conn=getConnection(dbInfo);
			DataSource dataSource=new SingleConnectionDataSource(conn,false);
			NamedParameterJdbcTemplate jdbc=new NamedParameterJdbcTemplate(dataSource);
			StringBuffer sql = new StringBuffer("select SO.name as TableName, SC.name as TableCloumn, ST.name as DataType, ");
			sql.append(" SC.isnullable as IsNull, SC.length as DataLength, SC.xscale as Scale,");
			sql.append(" ( ");
			sql.append(" SELECT COUNT(1) AS Is_PK  ");
			sql.append(" FROM  syscolumns   ");
			sql.append(" JOIN  sysindexkeys  ON   syscolumns.id=sysindexkeys.id  AND  syscolumns.colid=sysindexkeys.colid  ");  
			sql.append(" JOIN  sysindexes   ON   syscolumns.id=sysindexes.id  AND  sysindexkeys.indid=sysindexes.indid   ");
			sql.append(" JOIN  sysobjects   ON   sysindexes.name=sysobjects.name  AND  sysobjects.xtype='PK' ");
			sql.append(" WHERE syscolumns.name=SC.name AND syscolumns.id=object_id(SO.name) ");
			sql.append(" ) as IsPrimaryKey, ");
			sql.append(" ( ");
			sql.append(" 	SELECT COUNT(1) FROM INFORMATION_SCHEMA.columns ");
			sql.append(" 	WHERE TABLE_NAME=SO.name AND  COLUMNPROPERTY( ");
			sql.append(" 	OBJECT_ID(SO.name),COLUMN_NAME,'IsIdentity')=1 ");
			sql.append(" 	AND COLUMN_NAME = SC.name ");
			sql.append(" ) as IsAuto, ");
			sql.append(" SC.colid as CloumnIndex  ");
			sql.append(" from sysobjects SO  ");
			sql.append(" inner join syscolumns SC on SO.id = SC.id  and  SO.xtype = 'U' and  SO.status >= 0 and SO.name= '"+tableName+"' ");
			sql.append(" inner join systypes ST on SC.xtype = ST.xusertype  ");
			sql.append(" order by SO.name asc, SC.colorder asc ");
			dataInfo = jdbc.getJdbcOperations().query(sql.toString(), new ResultSetExtractor<List<FieldDetailInfo>>(){

				@Override
				public List<FieldDetailInfo> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<FieldDetailInfo> list = new ArrayList<FieldDetailInfo>();
					while(rs.next()){
						FieldDetailInfo fieldDetailInfo = new FieldDetailInfo();
						fieldDetailInfo.setTableName(rs.getString("TableName"));
						fieldDetailInfo.setTableCloumn(rs.getString("TableCloumn"));
						fieldDetailInfo.setDataType(rs.getString("DataType"));
						fieldDetailInfo.setIsPrimaryKey(rs.getInt("IsPrimaryKey"));
						fieldDetailInfo.setCloumnIndex(rs.getInt("CloumnIndex"));
						fieldDetailInfo.setDataLength(rs.getInt("DataLength"));
						fieldDetailInfo.setIsAuto(rs.getInt("IsAuto"));
						fieldDetailInfo.setIsNull(rs.getInt("IsNull"));
						fieldDetailInfo.setScale(rs.getInt("Scale"));
						list.add(fieldDetailInfo);
					}
					rs.close();
					return list;
				}
				
			});
		}catch(Exception ex){
			System.err.println(ex.getMessage());
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dataInfo;
	}
	
	/**
	 * 获取指定表中所有数据
	 * 
	 * */
	public static List<Object> getTableDataObject(String fields, String tableName, DBInfo dbInfo) {
		Connection conn=null;
		List<Object> list = new ArrayList<Object>();
		try{
			conn=getConnection(dbInfo);
			DataSource dataSource=new SingleConnectionDataSource(conn,false);
			NamedParameterJdbcTemplate jdbc=new NamedParameterJdbcTemplate(dataSource);
			StringBuffer sql = new StringBuffer("select "+fields+" from "+tableName);
			list = jdbc.getJdbcOperations().queryForList(sql.toString(), Object.class);
		}catch(Exception ex){
			System.err.println(ex.getMessage());
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 获取指定表中所有数据
	 * 
	 * */
	public static List<List<String>> getTableData(String fields, String tableName, DBInfo dbInfo) {
		System.err.println("数据库："+dbInfo.getDbName());
		Connection conn=null;
		List<List<String>> list = new ArrayList<List<String>>();
		try{
			conn=getConnection(dbInfo);
			DataSource dataSource=new SingleConnectionDataSource(conn,false);
			NamedParameterJdbcTemplate jdbc=new NamedParameterJdbcTemplate(dataSource);
			StringBuffer sql = new StringBuffer("select "+fields+" from "+tableName);
			System.err.println("data result:sql="+sql.toString());
			list = jdbc.getJdbcOperations().query(sql.toString(), new ResultSetExtractor<List<List<String>>>(){
				@Override
				public List<List<String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<List<String>> list = new ArrayList<List<String>>();
					while(rs.next()){
						List<String> listc = new ArrayList<String>();
						int cols = fields.split(",").length;
						System.err.println("返回列数为："+cols);
						String str = "";
						for (int i = 1; i <= cols; i++) {
							if(rs.getString(i) != null)
								str = rs.getString(i).trim();
							else
								str = rs.getString(i);
							listc.add(str);
							System.err.println(i+"结果集："+str);
						}
						list.add(listc);
					}
					System.err.println(list.size());
					return list;
				}
			});
		}catch(Exception ex){
			System.err.println(ex.getMessage());
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public static String perform(String sql, DBInfo dbInfo){
		sql.replace("null", "");
		Connection conn=null;
		Integer re = 0;
		try {
			conn=getConnection(dbInfo);
			DataSource dataSource=new SingleConnectionDataSource(conn,false);
			NamedParameterJdbcTemplate jdbc=new NamedParameterJdbcTemplate(dataSource);
			re = jdbc.update(sql, new HashMap<String, String>());
		}catch(Exception ex){
			System.err.println(ex.getMessage());
		}finally{
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return "返回结果："+re;
	}
}
