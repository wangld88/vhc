package com.vhc.controller.error;

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.vhc.util.Message;

@RestController
public class RestErrorController implements ErrorController {

    private static final String PATH = "/error/error";

    @Value("${includestacktrace}")
    private boolean debug=true;

    @Autowired
    private ErrorAttributes errorAttributes;

    private static final Logger LOGGER = LoggerFactory.getLogger("RestErrorController");


    /*@RequestMapping(value = PATH)
    ErrorJson restError(HttpServletRequest request, HttpServletResponse response) {
        // Appropriate HTTP response code (e.g. 404 or 500) is automatically set by Spring.
        // Here we just define response body.
    	LOGGER.debug("There is an error in RestErrorController: "+response.getStatus());
    	System.out.println("!!!There is an error in RestErrorController request.getContextPath(): "+request.getContextPath());
        return new ErrorJson(response.getStatus(), getErrorAttributes(request, debug));
    }*/

    @RequestMapping(value = PATH)
    public ModelAndView error(HttpServletRequest request, WebRequest webRequest, HttpServletResponse response) {
        // Appropriate HTTP response code (e.g. 404 or 500) is automatically set by Spring.
        // Here we just define response body.
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());

	        /*if(statusCode == HttpStatus.FORBIDDEN.value()) {
	            return new ModelAndView("error/403");
	        } else */if(statusCode == HttpStatus.NOT_FOUND.value()) {
	            return new ModelAndView("error/404");
	        } else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	            return new ModelAndView("error/500");
	        } else {
	        	Map<String, Object> att = getErrorAttributes(webRequest, debug);
	        	LOGGER.debug("There is an error in RestErrorController: "+response.getStatus());
	        	return setErrorView(request, response, att, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    } else {
	    	return new ModelAndView("error");
	    }
    }


	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
	    return "error";
	}


    //@Override
    public String getErrorPath() {
        return PATH;
    }

    private Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        //RequestAttributes requestAttributes = new ServletRequestAttributes(request);
    	ErrorAttributeOptions options = ErrorAttributeOptions.defaults();
    	if(includeStackTrace) {
    		options.including(ErrorAttributeOptions.Include.MESSAGE);
    	}
        return errorAttributes.getErrorAttributes(webRequest, options);
    }

    private ModelAndView setErrorView(HttpServletRequest request, HttpServletResponse response, Map<String, Object> att, HttpStatus httpStatus) {
        response.setStatus(httpStatus.value());

        ModelAndView mav = new ModelAndView();
        Message message = new Message();

        if (att != null) {
        	String view = "/error/error";
        	mav.setViewName(view);
        	String msg = (String) att.get("message");
        	if(msg != null && (msg.contains("Invalid CSRF Token") || msg.contains("CSRF token not found"))) {
        		message.setMessage("Your session is expired, please try again");
                mav.addObject("stackTrace", "Your session is expired, please <a href='" + (String) att.get("path") + "'>Login</a> again");
        	} else {
                message.setMessage((String)att.get("error"));
                mav.addObject("stackTrace", att.get("trace"));
        	}
            message.setStatus(Message.ERROR);
            mav.addObject("title", att);
            mav.addObject("errorMessage", att.get("error"));
            mav.addObject("message", message);

            LOGGER.debug("ERROR found in REST");
        }
        mav.addObject("content", request.getRequestURL());

        return mav;
    }
}
