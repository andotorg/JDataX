package org.andot.jdatax.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import org.andot.jdatax.entity.DBInfo;
import org.andot.jdatax.entity.FieldDetailInfo;
import org.andot.jdatax.service.DataTransferService;
import org.andot.jdatax.utils.DataBaseOperation;

@Service("dataTransferService")
public class DataTransferServiceImpl implements DataTransferService {
	
	@Override
	public String dataTransfer(List<DBInfo> dblist, List<String> tables, String fieldsParam) throws Exception{
		if(tables.size() > 1){
			for (int j = 0; j < tables.size(); j++) {  //循环表
				List<List<String>> list1 = new ArrayList<List<String>>();
				List<List<String>> list2 = new ArrayList<List<String>>();
				if(fieldsParam == null){
					//1、先获取出此表在原来库中所有的字段
					List<FieldDetailInfo> listCol = DataBaseOperation.getTableColumDetail(tables.get(j), dblist.get(0));
					//2.1判断目标库中有没有此表，如果没有表，先创建表，创建表之后，进行数据插入
					boolean mbb = DataBaseOperation.getTableCount(tables.get(j).toLowerCase(), dblist.get(1));
					if(mbb){
						//创建表
						String sql = "CREATE TABLE IF NOT EXISTS `"+listCol.get(0).getTableName()+"`( ";
						String pk = "";
						for (int l = 0; l < listCol.size(); l++) {
							if(listCol.get(l).getIsAuto() == 1) {
								
								sql += " `"+listCol.get(l).getTableCloumn()+"` "
									+ listCol.get(l).getDataType().toLowerCase()+" UNSIGNED AUTO_INCREMENT, ";
							}else if(listCol.get(l).getDataType().toLowerCase().equals("data")
									|| listCol.get(l).getDataType().toLowerCase().equals("datatime")
									|| listCol.get(l).getDataType().toLowerCase().equals("smalldatetime")) {
								
								sql += " `"+listCol.get(l).getTableCloumn()+"` "+listCol.get(l).getDataType()+", ";
							}else if (listCol.get(l).getDataType().toLowerCase().equals("float")
									|| listCol.get(l).getDataType().toLowerCase().equals("double")
									|| listCol.get(l).getDataType().toLowerCase().equals("decimal")) {
								sql += " `"+listCol.get(l).getTableCloumn()+"` "+listCol.get(l).getDataType()+"("+listCol.get(l).getDataLength()+", "+listCol.get(l).getScale()+"), ";
							}else{
								if(listCol.get(l).getDataLength() != 0)
									sql += " `"+listCol.get(l).getTableCloumn()+"` "+listCol.get(l).getDataType()+"("+listCol.get(l).getDataLength()+") ";
								else
									sql += " `"+listCol.get(l).getTableCloumn()+"` "+listCol.get(l).getDataType();
								if(listCol.get(l).getIsNull() == 0)  //0是不允许为空
									sql += " NOT NULL"; 
								sql += ", ";
							}
							sql.substring(0, sql.length()-2);
							if(listCol.get(l).getIsPrimaryKey() == 1)
								pk = " PRIMARY KEY ( `"+listCol.get(l).getTableCloumn()+"` ) ";
						}
						sql += pk + " )ENGINE=InnoDB DEFAULT CHARSET=utf8; ";
						sql = sql.replace("smalldatetime", "datetime");
						//插入数据
						System.err.println("创建表语句"+sql);
						DataBaseOperation.perform(sql, dblist.get(1));
					}
					//不管有没有表都需要执行，因为没有就创建表，创建完成之后还得需要插入数据
					String fields = "";
					for (FieldDetailInfo fieldDetailInfo : listCol) {
						fields += fieldDetailInfo.getTableCloumn()+", ";
					}
					//2.2如果有表，就取出两张表的数据取出差集，插入目标库
					list1 = DataBaseOperation.getTableData(fields.substring(0, fields.length()-2), tables.get(j), dblist.get(0));
					list2 = DataBaseOperation.getTableData(fields.substring(0, fields.length()-2), tables.get(j), dblist.get(1));
					System.err.println("list1-front:"+list1.size());
					System.err.println("list2:"+list2.size());
					list1.removeAll(list2);
					System.err.println("list1-front:"+list1.size());
					if(list1.size()>0){
						System.err.println("//把差集的数据插入目标库");
						fields = fields.substring(0, fields.length()-2);
						
						//去除主键自增
						String delPrimary ="ALTER TABLE `mine_survey_info` " + 
								"MODIFY COLUMN `MINE_SURVEY_ID`  int(10) UNSIGNED NOT NULL FIRST , " + 
								"DROP PRIMARY KEY;";
						DataBaseOperation.perform(delPrimary, dblist.get(1));
						String[] arrFields = fields.split(",");
						for (int i = 0; i < list1.size(); i++) {
							String field = "", value = "";
							for (int z=0; z<list1.get(i).size(); z++) {
								System.err.println(list1.get(i).get(z));
								if(list1.get(i).get(z) == null || list1.get(i).get(z).equals("")) {
									System.err.println("值为null，不插入");
								}else {
									if(listCol.get(z).getDataType().trim().toLowerCase().equals("varchar")
											|| listCol.get(z).getDataType().toLowerCase().equals("nvarchar")
											|| listCol.get(z).getDataType().toLowerCase().equals("nchar")) {
										value += "'"+list1.get(i).get(z)+"', ";
									}else if(listCol.get(z).getDataType().toLowerCase().equals("data")
											|| listCol.get(z).getDataType().toLowerCase().equals("datatime")
											|| listCol.get(z).getDataType().toLowerCase().equals("smalldatetime")) {
										value += "'"+list1.get(i).get(z)+"', ";
									}
									else {
										value += list1.get(i).get(z)+", ";
									}
									field += arrFields[z] + ", ";
								}
							}
							field = field.substring(0, field.length()-2);
							value = value.substring(0, value.length()-2);
							System.err.println("field:"+field.split(",").length+" ===== value:"+value.split(",").length);
							
							String insertSql = " INSERT INTO "+tables.get(j)+"("+field+") VALUES ("+value+")";
							DataBaseOperation.perform(insertSql, dblist.get(1));
						}
						
						//查询最大自增到几了
						String pka = "", pkt = "";
						int pkc = 0;
						List<Object> list = new ArrayList<Object>();
						for (int i = 0; i < listCol.size(); i++) {
							if(listCol.get(i).getIsPrimaryKey() == 1) {
								pka = listCol.get(i).getTableCloumn();
								pkc = listCol.get(i).getDataLength();
								pkt = listCol.get(i).getDataType();
								list = DataBaseOperation.getTableDataObject(" MAX("+listCol.get(i).getTableCloumn()+") ", tables.get(j), dblist.get(1));
								break;
							}
						}
						
						//加入主键自增
						String addPrimary ="ALTER TABLE `mine_survey_info` " + 
								"MODIFY COLUMN `"+pka+"`  "+pkt+"("+pkc+") UNSIGNED NOT NULL AUTO_INCREMENT FIRST , " + 
								"ADD PRIMARY KEY (`"+pka+"`);";
						if(list.get(0) != null) {
							addPrimary += "ALTER TABLE `mine_survey_info` " + 
									"AUTO_INCREMENT="+list.get(0)+";";
						}
						DataBaseOperation.perform(addPrimary, dblist.get(1));
					}else{
						System.err.println("没有差集");
					}
				}else{
					System.err.println("//指定列的时候");
				}
			}
		}else{
			List<List<String>> list1 = new ArrayList<List<String>>();
			List<List<String>> list2 = new ArrayList<List<String>>();
			for (int i = 0; i < dblist.size(); i++) {
				if(fieldsParam == null){
					
				}else{
					List<List<String>> list = new ArrayList<List<String>>();
					list = DataBaseOperation.getTableData(fieldsParam, tables.get(0), dblist.get(i));
					if(i == 0){
						list1 = list;
					}else{
						list2 = list;
					}
				}
			}
			if(list1.removeAll(list2)){
				//把差集的数据插入目标库
			}
		}
		
		return "success";
	}
}
