package org.andot.jdatax.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Log {
	
	private Logger logger = LoggerFactory.getLogger(Log.class);
	
	private String nowDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	
	public void info(Object obj, String msg){
		logger.info("["+nowDate()+"] " + obj.getClass().getName() + msg);
	}
	
	public void err(Object obj, String msg){
		logger.error("["+nowDate()+"] " + obj.getClass().getName() + msg);
	}
	
	public void debug(Object obj, String msg){
		logger.debug("["+nowDate()+"] " + obj.getClass().getName() + msg);
	}
	
	public void warn(Object obj, String msg){
		logger.warn("["+nowDate()+"] " + obj.getClass().getName() + msg);
	}
}
