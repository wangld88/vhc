package com.vhc.controller.admin;

import com.vhc.security.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class AdminLoginControllerAdvice {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminLoginControllerAdvice.class);
  
	@ModelAttribute("LoginUser")
	public LoginUser getCurrentUser(Authentication authentication) {
		logger.info(" authentication: " + authentication);
		LoginUser user = authentication == null ? null : (LoginUser)authentication.getPrincipal();
		String password = user == null ? null : user.getPassword();
		logger.info("AdminLoginControllerAdvice is invoked: " + password);
    
		return authentication == null ? null : (LoginUser)authentication.getPrincipal();
	}
	
}
