package org.andot.jdatax.service.settings;

public enum SysConfig {
	mysql(1, "com.mysql.jdbc.Driver"), 
	oracle(2, "oracle.jdbc.driver.OracleDriver"), 
	sqlserver(3, "com.microsoft.sqlserver.jdbc.SQLServerDriver");  //创建枚举类型
	
	private int configId;   //枚举Id
	private String configName; //
	
	SysConfig (int configId){  //创建构造函数用来定义枚举类型
		this.configId = configId;
	}
	
	SysConfig(int configId, String configName) {  //获取枚举id的方法
		this.configId = configId;
		this.configName = configName;
	}

	public int getConfigId() {
		return configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	
}
