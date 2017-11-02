package com.vhc.controller.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vhc.controller.BaseController;

@Controller
@RequestMapping({"/store"})
public class StoreLogin extends BaseController {
  
	private static final Logger logger = LoggerFactory.getLogger(StoreLogin.class);
  
	@RequestMapping(method={RequestMethod.GET}, value={"/errorpage/{errorid}"})
	public String globalerrorpage(ModelMap model, @PathVariable("errorid") Long errorid) {
		
		logger.info("globalerrorpage is called");
		String toRet = "error";
		model.addAttribute("errorid", errorid);
		model.addAttribute("title", "Error");
    
		return toRet;
	}
  
	@RequestMapping(method={RequestMethod.GET}, value={"/login"})
	public String dspLogin(ModelMap model) {
		return "store/login/login";
	}
	
}
