package com.vhc.controller.admin;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vhc.core.model.User;


@Controller
@RequestMapping("/admin")
public class AdminCalendar extends AdminBase {

	@RequestMapping(method={RequestMethod.GET}, value={"/calendar"})
	public String dspCalendar(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/calendar";
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			//logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		model.addAttribute("loginUser", getPrincipal());
		model.addAttribute("adminmenu", "Schedules");

		return rtn;
	}

}
