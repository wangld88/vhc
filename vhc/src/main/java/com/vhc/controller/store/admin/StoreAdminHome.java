package com.vhc.controller.store.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.vhc.controller.store.StoreBase;
import com.vhc.core.model.User;
import com.vhc.security.LoginUser;


/**
 *
 * This is the home controller for Store related pages
 *
 * @author Jerry
 *
 */
@Controller
@RequestMapping({"/store/admin"})
public class StoreAdminHome extends StoreBase {

	private final Logger logger = LoggerFactory.getLogger(StoreAdminHome.class);


	@RequestMapping(method={RequestMethod.GET}, value={"/","/home"})
	public String dspHome(ModelMap model, HttpSession httpSession) {
		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Home");

		return "store/admin/home";
	}


    @RequestMapping(method = RequestMethod.GET, value="/*")
    public void handleInvalidRequest(HttpServletRequest request)
    	throws NoHandlerFoundException {

		ModelMap model = new ModelMap();
		Object principal = getPrincipal();

		/*if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}*/

		User loginUser = getLoginUser(principal);

		model.addAttribute("loginUser", loginUser);

		throw new NoHandlerFoundException(request.getMethod(), request.getRequestURL().toString(), new HttpHeaders());
    }


    private Object getPrincipal() {

		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}


	private User getLoginUser(Object principal) {

		User user = null;

        if (principal instanceof LoginUser) {
            user = ((LoginUser)principal).getUser();
        } else {
            user = userService.getByUsername("");
        }

        return user;
    }

	private boolean isStoreAdmin(Object principal) {

		if(principal instanceof LoginUser) {
			return ((LoginUser) principal).getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
		} else {
			return false;
		}
	}

}
