package com.vhc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {

	@RequestMapping("/")
	public String dspHome() {
		return "index";
	}
}
