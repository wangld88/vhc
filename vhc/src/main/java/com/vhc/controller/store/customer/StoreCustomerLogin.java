package com.vhc.controller.store.customer;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vhc.util.Message;
import com.vhc.model.User;
import com.vhc.service.PasswordtokenService;
import com.vhc.controller.BaseController;
import com.vhc.dto.ForgetPasswordForm;

@Controller
@RequestMapping({"/customer"})
public class StoreCustomerLogin extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(StoreCustomerLogin.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PasswordtokenService passwordtokenService;

	@RequestMapping(method={RequestMethod.GET}, value={"/errorpage/{errorid}"})
	public String globalerrorpage(ModelMap model, @PathVariable("errorid") Long errorid) {

		logger.info("globalerrorpage is called");
		String toRet = "error";
		model.addAttribute("errorid", errorid);
		model.addAttribute("title", "Error");

		return toRet;
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/","/login"})
	public String dspLogin(ModelMap model, HttpSession session) {
		return "customer/login/login";
	}

	@RequestMapping(value = "/login/forgetPassword", method = RequestMethod.GET)
	public String dspForgetPassword(Model model) {
		logger.debug("Call forgetPassword");

		model.addAttribute("forgetpasswordForm", new ForgetPasswordForm());

		return "/customer/login/forgetPassword";
	}

	@RequestMapping(value = "/login/forgetPassword", method = RequestMethod.POST)
	public String doForgetPassword(@Valid @ModelAttribute("forgetpasswordForm") ForgetPasswordForm form,
			BindingResult bindingResult, Model model, final HttpServletRequest request) {
		Message message = new Message();
		String rtn = "/customer/login/forgetPassword";
		String username = form.getusername();

		logger.debug("Call doForgetPassword POST " + username);

		try {

			if (bindingResult.hasErrors()) {

				logger.info("Forget Password form validation failed!!!!!!!!");
				List<ObjectError> errors = bindingResult.getAllErrors();
				String msg = messageSource.getMessage("customer.forgetPassword.validation", new Object[] {},
						LocaleContextHolder.getLocale()) + "<br/>";
				for (ObjectError i : errors) {
					if (i instanceof FieldError) {
						FieldError fieldError = (FieldError) i;
						msg += messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()) + "<br/>";
					}
				}
				message.setStatus(Message.ERROR);
				message.setMessage(msg);
				model.addAttribute("message", message);

				return "/customer/login/forgetPassword";
			}

			final String token = UUID.randomUUID().toString();
			User emhcuser = userService.findByUsername(username);

			if (emhcuser.getUserid() > 0) {
				String url = getAppUrl(request);
				String tokenURL = url + "/customer/login/resetPassword?id=" + emhcuser.getUserid() + "&token=" + token;

				passwordtokenService.createPasswrodtokenForUser(emhcuser, token);

				String from = "scjimcc@gmail.com";
				String to = emhcuser.getEmail();
				String subject = "Reset your Password!";
				String body = tokenURL;

				//emailService.sendMail(from, to, subject, body);

				rtn = "/customer/login/sendPasswordSuccess";

				message.setStatus(Message.SUCCESS);
				message.setMessage(messageSource.getMessage("StudentLogin.forgetPassword.success", new Object[] {},
					LocaleContextHolder.getLocale()));
				return rtn;

			} else {

				logger.debug("Could not find the customer based on provided information - studentnumber: " + username);
				rtn= "/customer/login/forgetPassword";
				message.setStatus(Message.ERROR);
				message.setMessage(messageSource.getMessage("StudentLogin.forgetPassword.failure", new Object[] {},
						LocaleContextHolder.getLocale()));
			}

		} catch (Exception e) {
			logger.debug("Error in /customer/login/forgetPassword of StudentLogin.  Error: " + e.getMessage());
			message.setStatus(Message.ERROR);
			message.setMessage(messageSource.getMessage("StudentLogin.forgetPassword.error", new Object[] {},
					LocaleContextHolder.getLocale()));
		}

		model.addAttribute("message", message);

		return rtn;
	}

	@RequestMapping(value={"/logout","/login/logout"}, method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	    if (auth != null) {
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }

	    return "redirect:/customer/login?logout";
	}

	private String getAppUrl(HttpServletRequest request) {
		if (request.isSecure()) {
			return "https://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		} else {
			return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		}
	}

}
