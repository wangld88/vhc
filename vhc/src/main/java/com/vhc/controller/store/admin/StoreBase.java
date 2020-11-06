package com.vhc.controller.store.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.thymeleaf.exceptions.TemplateProcessingException;

import com.vhc.controller.BaseController;


public class StoreBase extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(StoreBase.class);

	protected static final String ERROR_VIEW = "store/admin/error/error";


	protected boolean hasRole(String role) {
        // get security context from thread local
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null)
            return false;

        Authentication authentication = context.getAuthentication();
        if (authentication == null)
            return false;

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if (role.equals(auth.getAuthority()))
                return true;
        }

        return false;
    }

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


	/*@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView handleNoHandlerFoundException(NoHandlerFoundException e) {

		final ModelAndView model = initModelView(null);

		//model.addObject(RETURN_MESSAGE, new Message(Message.ERROR, "The requested resource could not be found!"));
		logger.info("Error: " + ((NoHandlerFoundException) e).getRequestURL());
		logger.info("Error: " + ((NoHandlerFoundException) e).getCause());
		logger.info("Error: " + e.getMessage());
		Map<String, Object> att = new HashMap<>();
    	att.put("requestURL", e.getRequestURL());
		att.put("title", "No such page found!");
    	att.put("trace", e.getStackTrace());
    	att.put("message", e.getMessage());

    	model.setStatus(HttpStatus.NOT_FOUND);
		model.addObject("exception", att);

		return model;
    }*/


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

		model.addObject("errordetails", errorDetails);

		return model;

	}

	/*public Calendar parseDate(String date) {

		Calendar rtn = Calendar.getInstance();

		try {

			SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

			Date inputDate = formatter.parse(date);

			rtn.setTime(inputDate);

			logger.info(String.format("Parsed Date: %s", rtn.getTime()));

		} catch(Exception e) {
			logger.error(String.format("Error in parseDate: %s", e.getMessage()));;
		}

		return rtn;
	}*/

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

}
