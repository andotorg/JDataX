package org.andot.jdatax.dao.base;

import org.apache.ibatis.annotations.Mapper;

import org.andot.jdatax.entity.DBInfo;

@Mapper
public interface DBInfoMapper {
	
	void addDBInfo(DBInfo dbInfo);
	
}
