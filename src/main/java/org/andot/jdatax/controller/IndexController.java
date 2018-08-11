package org.andot.jdatax.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
	
	/**
	 * 首页
	 * 
	 * */
	@GetMapping("/")
	public void index(HttpServletRequest request, HttpServletResponse response){
		try {
			request.getRequestDispatcher("/index.html").forward(request, response);
		} catch (ServletException e) {
			System.err.println("跳转错误："+e.getMessage());
		} catch (IOException e) {
			System.err.println("输入输出错误："+e.getMessage());
		}
	}

}
