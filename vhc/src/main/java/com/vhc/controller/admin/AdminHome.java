package com.vhc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping({"/admin"})
public class AdminHome {

	@RequestMapping(method={RequestMethod.GET}, value={"/home"})
	public String dspHome() {
		return "admin/index";
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/charts"})
	public String dspCharts() {
		return "admin/charts";
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/profile"})
	public String dspProfile() {
		return "admin/profile";
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/form_component"})
	public String dspFormComponent() {
		return "admin/form_component";
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/form_validation"})
	public String dspFormValidation() {
		return "admin/form_validation";
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/widgets"})
	public String dspWidgets() {
		return "admin/widgets";
	}

}
