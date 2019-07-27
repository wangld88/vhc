package com.vhc.controller.store;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.thymeleaf.exceptions.TemplateProcessingException;

import com.vhc.controller.BaseController;
import com.vhc.util.Message;


public class StoreBase extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(StoreBase.class);

	protected static final String ERROR_VIEW = "store/admin/error/error";

	private static final String ALERT_MESSAGE = "errorMessage";

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
	public String handleTemplateProcessingException(ModelMap model, Exception e) {

		//System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		//model.setStatus(HttpStatus.NOT_FOUND);
		model.addAttribute("errordetails", "No such page found!");
		//model.addObject(RETURN_MESSAGE, new Message(Message.ERROR, "The requested resource could not be found!"));
		logger.info("Error: " + e.getMessage());
		model.addAttribute(ALERT_MESSAGE, new Message(Message.ERROR, "The requested page could not be found!"));

        return "/error/error";
    }


	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String methodNotSupportExceptionHandling(HttpServletRequest request,
    		HttpServletResponse response,
    		ModelMap model,
    		HttpRequestMethodNotSupportedException e) {

        //final ModelAndView model = initModelView(null);
        logger.error("[x] HttpRequestMethodNotSupportedException: Http Request Method Not Supported!");
        //model.setStatus(HttpStatus.NOT_FOUND);
        model.addAttribute("errordetails", "No such page found!");
        model.addAttribute(ALERT_MESSAGE, new Message(Message.ERROR, "The requested method could not be found!"));

        return "/error/error";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFoundException(ModelMap model, Exception e) {

        logger.error("[x] NoHandlerFoundException: No such page found!");

        model.addAttribute("errordetails", "Bad Request!");
        model.addAttribute(ALERT_MESSAGE, new Message(Message.ERROR, "The requested resource could not be found!"));

        return "/error/error";
    }


	@ExceptionHandler({ Exception.class })
	public String uncaughtExceptionHandling (Exception e) {

		logger.info("General Error is caught" + e.getMessage());
		e.printStackTrace();
		ModelMap model = new ModelMap();

		String errorDetails = "N/A";

		if (e != null) {
			errorDetails = e.getMessage();

			if (e.getCause() != null && e.getCause().getMessage() != null) {
				errorDetails += " - " + e.getCause().getMessage();
			}

		}
        model.addAttribute("errordetails", errorDetails);
        model.addAttribute(ALERT_MESSAGE, new Message(Message.ERROR, "Unexpected Error!"));

        return "/error/error";
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
