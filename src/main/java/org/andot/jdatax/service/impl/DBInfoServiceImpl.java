package org.andot.jdatax.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.andot.jdatax.dao.base.DBInfoMapper;
import org.andot.jdatax.entity.DBInfo;
import org.andot.jdatax.service.DBInfoService;

@Transactional(value = "baseDataSourceTransactionManager",
	propagation = Propagation.REQUIRED,
	isolation = Isolation.DEFAULT,
	timeout=36000,
	rollbackFor=Exception.class)
@Service("dbInfoService")
public class DBInfoServiceImpl implements DBInfoService {
	
	@Autowired
	private DBInfoMapper dbInfoDao;

	@Override
	public int addDBInfo(DBInfo dbInfo) {
		dbInfoDao.addDBInfo(dbInfo);
		return 1;
	}

}
