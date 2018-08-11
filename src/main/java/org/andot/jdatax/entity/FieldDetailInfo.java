package org.andot.jdatax.entity;

public class FieldDetailInfo {
	private String tableName;   //表名
	private String tableCloumn;  //列明
	private String dataType; //字段类型
	private Integer isPrimaryKey; //是否为主键
	private Integer cloumnIndex; //字段顺序
	private Integer dataLength;  //字段长度
	private Integer isAuto;   //是否为自增主键
	private Integer isNull;   //是否为空
	private Integer Scale;   //小数后几位
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableCloumn() {
		return tableCloumn;
	}
	public void setTableCloumn(String tableCloumn) {
		this.tableCloumn = tableCloumn;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public Integer getIsPrimaryKey() {
		return isPrimaryKey;
	}
	public void setIsPrimaryKey(Integer isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}
	public Integer getCloumnIndex() {
		return cloumnIndex;
	}
	public void setCloumnIndex(Integer cloumnIndex) {
		this.cloumnIndex = cloumnIndex;
	}
	public Integer getDataLength() {
		return dataLength;
	}
	public void setDataLength(Integer dataLength) {
		this.dataLength = dataLength;
	}
	public Integer getIsAuto() {
		return isAuto;
	}
	public void setIsAuto(Integer isAuto) {
		this.isAuto = isAuto;
	}
	public Integer getIsNull() {
		return isNull;
	}
	public void setIsNull(Integer isNull) {
		this.isNull = isNull;
	}
	public Integer getScale() {
		return Scale;
	}
	public void setScale(Integer scale) {
		Scale = scale;
	}
	
}
