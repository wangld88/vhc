package com.vhc.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vhc.controller.BaseController;


@Controller
@RequestMapping({"/admin"})
public class AdminLogin extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AdminLogin.class);

	@RequestMapping(method={RequestMethod.GET}, value={"/login"})
	public String dspLogin(ModelMap model) {

		String loginUser = getPrincipal();

		if(loginUser != null && loginUser != "anonymousUser") {
			return "redirect:home";
		} else {
			return "admin/login/login";
		}
	}

	@RequestMapping(value={"/Access_Denied"}, method={RequestMethod.GET})
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		return "/error/accessdenied";
	}


	@RequestMapping(method={RequestMethod.GET}, value={""})
	public String dspHome(ModelMap model) {
		model.addAttribute("loginUser", getPrincipal());
		return "index";
	}


	private String getPrincipal()	{
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if ((principal instanceof UserDetails)) {
			logger.info("Get Logged in User : " + ((UserDetails)principal).toString());
			userName = ((UserDetails)principal).getUsername();
		} else {
			logger.info("Get Logged in User : " + principal.toString());
			userName = principal.toString();
		}
		return userName;
	}
}
