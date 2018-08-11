package org.andot.jdatax.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

public class ResultJson {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private Integer flag;  //结果标志  0 失败  1成功  2异常
	private Object data;   //
	private String msg;
	private Integer size;
	private String date = sdf.format(new Date());
	
	public ResultJson (Integer flag, Object data, Integer size, String msg){
		this.flag = flag;
		this.data = data;
		this.msg = msg;
		this.size = size;
	}
	
	public static String getJsonMsg(ResultJson rjson){
		return JSONObject.fromObject(rjson).toString();
	}
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
	
}
