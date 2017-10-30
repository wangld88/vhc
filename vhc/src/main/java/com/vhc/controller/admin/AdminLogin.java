package com.vhc.controller.admin;

import com.vhc.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping({"/admin"})
public class AdminLogin extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminLogin.class);
  
	@RequestMapping(method={RequestMethod.GET}, value={"/login"})
	public String dspLogin(ModelMap model) {
		return "admin/login/login";
	}
  
	@RequestMapping(value={"/Access_Denied"}, method={RequestMethod.GET})
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		return "/error/accessDenied";
	}
  
	
	private String getPrincipal()	{
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
		logger.info("Get Logged in User : " + ((UserDetails)principal).toString());
		if ((principal instanceof UserDetails)) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
}
