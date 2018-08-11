package org.andot.jdatax.entity;

import java.io.Serializable;

public class DBInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer dbId;  //数据库id
	private String dbName;  //数据库名称
	private String dbUser;  //数据库用户
	private String dbPwd;   //数据库密码
	private String dbUrl;  // 数据库驱动类
	private Integer dbType;  // 数据库类型
	private String dbClass;  // 数据库类型
	public Integer getDbId() {
		return dbId;
	}
	public void setDbId(Integer dbId) {
		this.dbId = dbId;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getDbUser() {
		return dbUser;
	}
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	public String getDbPwd() {
		return dbPwd;
	}
	public void setDbPwd(String dbPwd) {
		this.dbPwd = dbPwd;
	}
	public String getDbUrl() {
		return dbUrl;
	}
	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}
	public Integer getDbType() {
		return dbType;
	}
	public void setDbType(Integer dbType) {
		this.dbType = dbType;
	}
	public String getDbClass() {
		return dbClass;
	}
	public void setDbClass(String dbClass) {
		this.dbClass = dbClass;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
