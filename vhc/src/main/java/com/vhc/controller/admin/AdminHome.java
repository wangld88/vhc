package com.vhc.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.vhc.core.model.User;



/**
 *
 * @author K & J Consulting
 *
 */
@Controller
@RequestMapping({"/admin"})
public class AdminHome extends AdminBase {

	private Logger logger = LoggerFactory.getLogger(AdminHome.class);

	@RequestMapping(method={RequestMethod.GET}, value={"/","/home"})
	public String dspHome(ModelMap model, HttpSession httpSession) {
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Home");
		model.addAttribute("submenu", "homes");

		return "admin/index";
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/charts"})
	public String dspCharts(ModelMap model, HttpSession httpSession) {
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		model.addAttribute("loginUser", loginUser);
		return "admin/charts";
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/profile"})
	public String dspProfile(ModelMap model, HttpSession httpSession) {

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		model.addAttribute("loginUser", loginUser);
		return "admin/profile";
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/form_component"})
	public String dspFormComponent(ModelMap model, HttpSession httpSession) {
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		model.addAttribute("loginUser", loginUser);
		return "admin/form_component";
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/form_validation"})
	public String dspFormValidation(ModelMap model, HttpSession httpSession) {
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		model.addAttribute("loginUser", loginUser);
		return "admin/form_validation";
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/widgets"})
	public String dspWidgets(ModelMap model, HttpSession httpSession) {
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		model.addAttribute("loginUser", loginUser);
		return "admin/widgets";
	}

	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/admin/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
	}


    @RequestMapping(method = RequestMethod.GET, value="/*")
    public void handleInvalidRequest(HttpServletRequest request)
    	throws NoHandlerFoundException {

		ModelMap model = new ModelMap();
		User loginUser = getSuperAdmin();

		model.addAttribute("loginUser", loginUser);
System.out.println("handleInvalidRequest=================");
		throw new NoHandlerFoundException(request.getMethod(), request.getRequestURL().toString(), new HttpHeaders());
    }


}
