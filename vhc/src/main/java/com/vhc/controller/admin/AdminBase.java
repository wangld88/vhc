package com.vhc.controller.admin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.thymeleaf.exceptions.TemplateProcessingException;

import com.vhc.controller.BaseController;
import com.vhc.core.model.User;
import com.vhc.security.LoginUser;


@Controller
@RequestMapping("/admin")
public class AdminBase extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AdminBase.class);

	protected static final String ERROR_VIEW = "admin/error/error";


	@ExceptionHandler(TemplateProcessingException.class)
	public ModelAndView handleTemplateProcessingException(Exception e) {

		final ModelAndView model = initModelView(null);

		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		model.setStatus(HttpStatus.NOT_FOUND);
		model.addObject("errordetails", "No such page found!");
		//model.addObject(RETURN_MESSAGE, new Message(Message.ERROR, "The requested resource could not be found!"));
		logger.info("Error: " + e.getMessage());

        return model;
    }


	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView handleRestNoHandlerFoundException(NoHandlerFoundException e) {

		final ModelAndView model = initModelView(null);

		logger.info("Error: " + e.getMessage());
		Map<String, Object> att = new HashMap<>();
    	att.put("url", e.getRequestURL());
    	att.put("requestURL", e.getRequestURL());
		att.put("title", "No such page found!");
    	att.put("trace", e.getStackTrace());
    	att.put("message", e.getMessage());
    	att.put("path", e.getHttpMethod());
    	att.put("error", e.getCause());

    	model.setStatus(HttpStatus.NOT_FOUND);
		model.addObject("exception", att);
		model.addObject("loginUser", getLoginUser());

        return model;
    }


	@ExceptionHandler({ Exception.class })
	public ModelAndView uncaughtExceptionHandling (Exception e, HttpServletResponse httpresponse) {

		logger.info("General Error is caught" + e.getMessage());
		e.printStackTrace();
		//String errorMsg = "";
		String errorDetails = "N/A";

		final ModelAndView model = initModelView(ERROR_VIEW);

		if (e != null) {
			errorDetails = e.getMessage();

			if (e.getCause() != null && e.getCause().getMessage() != null) {
				errorDetails += " - " + e.getCause().getMessage();
			}

		}
		model.addObject("loginUser", getLoginUser());
		model.addObject("errordetails", errorDetails);

		return model;

	}

	private ModelAndView initModelView(String view) {
		ModelAndView model = new ModelAndView(ERROR_VIEW);
		/*if(view != null && !view.isEmpty() && !ERROR_VIEW.equals(view)) {
			model = new ModelAndView(view);
		}

		Boolean isAdmin = userService.isAdmin();

		model.addObject(USER_INFO, user);
		model.addObject(IS_ADMIN, isAdmin);

		if(isAdmin) {
			List<Type> types = typeService.findAll();

			model.addObject(ADMIN_STAGE, STAGE_HOME);
			model.addObject(TYPES, types);
		}*/

		return model;
	}

	/*private User getPrincipal(){
    	User user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean isSuper = false;
        for (GrantedAuthority grantedAuthority : authorities) {
        	isSuper = grantedAuthority.getAuthority().equals("SUPERADMIN");
        }

        if (principal instanceof LoginUser) {
        	LoginUser auth = (LoginUser)principal;
        	if(isSuper) {
        		user = auth.getUser();
        	}
        } else {
            user = userService.getByUsername("");
        }
        return user;
    }*/

	protected Object getPrincipal(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

	protected User getLoginUser() {

		User user = null;
		Object principal = getPrincipal();

        if (principal instanceof LoginUser) {
            user = ((LoginUser)principal).getUser();
        } else {
            user = userService.getByUsername("");
        }

        return user;
    }

	protected User getLoginUser(Object principal) {

		User user = null;

        if (principal instanceof LoginUser) {
            user = ((LoginUser)principal).getUser();
        } else {
            user = userService.getByUsername("");
        }

        return user;
    }

	protected boolean isSuperAdmin(Object principal) {

		if(principal instanceof LoginUser) {
			return ((LoginUser) principal).getAuthorities().contains(new SimpleGrantedAuthority("SUPERADMIN"));
		} else {
			return false;
		}
	}

	protected User getSuperAdmin(){
		User user = null;
	    Object principal = getPrincipal();

	    Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
	    boolean isSuper = false;
	    for (GrantedAuthority grantedAuthority : authorities) {
	    	isSuper = grantedAuthority.getAuthority().equals("SUPERADMIN");
	    }

	    if (principal instanceof LoginUser) {
	    	LoginUser auth = (LoginUser)principal;
	    	if(isSuper) {
	    		user = auth.getUser();
	    	}
	    } else {
	        user = userService.getByUsername("");
	    }
	    return user;
	}

}
