package org.lv5.bvworks.numbers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	
	@RequestMapping("/home")
	public ModelAndView homePage() {
		return new ModelAndView("home");
	}
}
