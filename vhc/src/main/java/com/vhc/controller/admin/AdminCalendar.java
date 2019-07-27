package com.vhc.controller.admin;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vhc.controller.BaseController;
import com.vhc.core.model.User;
import com.vhc.security.LoginUser;


@Controller
@RequestMapping("/admin")
public class AdminCalendar extends BaseController {

	@RequestMapping(method={RequestMethod.GET}, value={"/calendar"})
	public String dspCalendar(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/calendar";
		model.addAttribute("loginUser", getPrincipal());
		model.addAttribute("adminmenu", "Schedules");

		return rtn;
	}

	private User getPrincipal(){
    	User user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //System.out.println("Role is : "+((LoginStudent)principal).toString());
        if (principal instanceof LoginUser) {
            user = ((LoginUser)principal).getUser();
        } else {
            user = userService.getByUsername("");
        }
        return user;
    }

}
