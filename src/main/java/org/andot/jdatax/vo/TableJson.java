package org.andot.jdatax.vo;

import net.sf.json.JSONObject;

public class TableJson {
	
	private Object data;   //
	private String msg;
	private Integer code;
	private Integer count;
	//private String date = sdf.format(new Date());
	
	public TableJson (Integer code, String msg, Integer count, Object data){
		this.data = data;
		this.msg = msg;
		this.code = code;
		this.count = count;
	}
	
	public String toString(){
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("count", count);
		json.put("msg", msg);
		json.put("data", data);
		return json.toString();
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

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
