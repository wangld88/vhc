package com.vhc.controller.admin;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vhc.controller.BaseController;
import com.vhc.model.User;
import com.vhc.security.LoginUser;


@Controller
@RequestMapping({"/admin"})
public class AdminHome extends BaseController {

	@RequestMapping(method={RequestMethod.GET}, value={"/home"})
	public String dspHome(ModelMap model, HttpSession httpSession) {
		model.addAttribute("loginUser", getPrincipal());
		return "admin/index";
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/charts"})
	public String dspCharts(ModelMap model, HttpSession httpSession) {
		model.addAttribute("loginUser", getPrincipal());
		return "admin/charts";
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/profile"})
	public String dspProfile(ModelMap model, HttpSession httpSession) {
		model.addAttribute("loginUser", getPrincipal());
		return "admin/profile";
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/form_component"})
	public String dspFormComponent(ModelMap model, HttpSession httpSession) {
		model.addAttribute("loginUser", getPrincipal());
		return "admin/form_component";
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/form_validation"})
	public String dspFormValidation(ModelMap model, HttpSession httpSession) {
		model.addAttribute("loginUser", getPrincipal());
		return "admin/form_validation";
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/widgets"})
	public String dspWidgets(ModelMap model, HttpSession httpSession) {
		model.addAttribute("loginUser", getPrincipal());
		return "admin/widgets";
	}

	private User getPrincipal(){
    	User user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //System.out.println("Role is : "+((LoginStudent)principal).toString());
        if (principal instanceof LoginUser) {
            user = ((LoginUser)principal).getUser();
        } else {
            user = userService.findByUsername("");
        }
        return user;
    }
	
}
