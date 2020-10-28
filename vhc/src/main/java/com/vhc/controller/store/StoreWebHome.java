package com.vhc.controller.store;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/store")
public class StoreWebHome extends StoreBase {

	private final Logger logger = LoggerFactory.getLogger(StoreWebHome.class);

	@RequestMapping(method={RequestMethod.GET}, value={"/","/home"})
	public String dspHome(ModelMap model, HttpSession httpSession) {
		String rtn = "store/admin/";

		logger.info("StoreWebHome will be redirected.");
		return "redirect:" +rtn;
	}

}
