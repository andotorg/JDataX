package org.andot.jdatax.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RootController {
	@RequestMapping("/")
	public ModelAndView index(Model model){
		return new ModelAndView("login");
	}
}
