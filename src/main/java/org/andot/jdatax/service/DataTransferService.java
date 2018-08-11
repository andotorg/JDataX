package org.andot.jdatax.service;

import java.util.List;

import org.andot.jdatax.entity.DBInfo;

public interface DataTransferService {
	
	String dataTransfer(List<DBInfo> dblist, List<String> tables, String fields) throws Exception;

}
