package com.vhc.controller.admin;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vhc.dto.TemplateForm;
import com.vhc.util.Message;
import com.vhc.core.model.Template;
import com.vhc.core.model.User;
import com.vhc.service.emailer.EmailContentService;


/**
 *
 * @author K & J Consulting
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminResource extends AdminBase {

	private final Logger logger = LoggerFactory.getLogger(AdminResource.class);


	@GetMapping("/templates")
	public String dspTemplates(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/templates";

		logger.info("dspTemplates is called");

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		List<Template> templates = templateService.getAll();

		model.addAttribute("templates", templates);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Resources");
		model.addAttribute("submenu", "templates");

		return rtn;
	}


	@RequestMapping(value="/template", method=RequestMethod.GET)
	public String newTemplate(Model model) {
		String rtn = "/admin/template";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		//Get methods Email Content
		Method[] methods = EmailContentService.class.getMethods();

		List<String> names = new ArrayList<>();

		for(int i=0; i < methods.length; i++) {
			if(methods[i].getName() != "getClass" && methods[i].getName().startsWith("get")) {
				names.add(methods[i].getName());
			}
		}

		logger.info("dspTemplates is called");

		TemplateForm template = new TemplateForm();
		template.setServicename("EmailContentService");

		model.addAttribute("methodnames", names);
		model.addAttribute("templateForm", template);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Resources");
		model.addAttribute("submenu", "templates");

		return rtn;
	}


	@RequestMapping(value="/template/{templateid}", method=RequestMethod.GET)
	public String dspTemplate(@PathVariable("templateid") Integer templateid,
							@ModelAttribute("errMessage") Message errMessage,
							Model model) {

		String rtn = "/admin/template";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		Template template = templateService.getById(templateid);

		TemplateForm form = new TemplateForm();
		form = new TemplateForm(template);

		if(errMessage != null) {
			model.addAttribute("message", errMessage);
		}

		//Get methods Email Content
		Method[] methods = EmailContentService.class.getMethods();

		List<String> names = new ArrayList<>();

		for(int i=0; i < methods.length; i++) {
			if(methods[i].getName() != "getClass" && methods[i].getName().startsWith("get")) {
				names.add(methods[i].getName());
			}
		}

		logger.info("[Admin Resource] dspTemplate is called {}", names.size());

		model.addAttribute("methodnames", names);
		model.addAttribute("templateForm", form);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Resources");
		model.addAttribute("submenu", "templates");
		//model.addAttribute("templateForm", form);

		return rtn;
	}


	@RequestMapping(value="/template", method=RequestMethod.POST)
	public String doTemplate(@Valid @ModelAttribute("templateForm") TemplateForm form,
							BindingResult bindingResult,
							Model model,
							HttpSession httpSession,
							RedirectAttributes ra) {

		//logger.info("Processing updateProfile form={}, bindingResult={}", form, bindingResult);
		String rtn = "/admin/templates";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}
		Message message = new Message();

		if (bindingResult.hasErrors()) {

			List<ObjectError> errors = bindingResult.getAllErrors();
			String msg = messageHandler.get("Header.templateForm.validation") + "<br />";

			for(ObjectError i: errors) {
				if(i instanceof FieldError) {
					FieldError fieldError = (FieldError) i;
					msg += messageHandler.get(fieldError.getCode()) + "<br />";
				}
			}

			model.addAttribute("templateForm", form);
			model.addAttribute("loginUser", loginUser);
			model.addAttribute("adminmenu", "Resources");
			model.addAttribute("submenu", "templates");

			message.setStatus(Message.ERROR);
			message.setMessage(msg);
			int templateid = form.getTemplateid();
			model.addAttribute("message", message);

			if(templateid == 0) {
				return "/admin/template";
			} else {
				ra.addFlashAttribute("errMessage", message);
				return "redirect: template/" + templateid;
			}
		} else {
			logger.info("Errors: "+bindingResult.getErrorCount());;
		}

		Template template = form.getTemplate();

		template = templateService.save(template);

		List<Template> templates = templateService.getAll();

		model.addAttribute("templates", templates);

		return "redirect:" + rtn;
	}


}
