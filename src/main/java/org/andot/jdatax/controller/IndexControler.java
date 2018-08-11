package org.andot.jdatax.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.andot.jdatax.entity.DBInfo;
import org.andot.jdatax.service.DBInfoService;

@Controller
@RequestMapping("/rest/jdatax/index")
public class IndexControler {
	
	@Resource
	private DBInfoService dbInfoService;
	
	@RequestMapping("")
    public String index(Model model){
		DBInfo dbInfo = new DBInfo();
		dbInfo.setDbName("mysql");
		dbInfo.setDbUser("root");
		dbInfo.setDbPwd("iesapp");
		dbInfo.setDbClass("1212");
		dbInfo.setDbType(1);
		dbInfoService.addDBInfo(dbInfo);
        model.addAttribute("singlePerson", "aaaa");
        return "index";
    }
}
