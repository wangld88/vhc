package com.vhc.controller.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vhc.core.model.Role;
import com.vhc.core.model.User;
import com.vhc.core.model.Userrole;


/**
 *
 * @author K & J Consulting
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminUser extends AdminBase {

	private static final Logger logger = LoggerFactory.getLogger(AdminUser.class);


	@RequestMapping(value="/users", method=RequestMethod.GET)
	public String dspUsers(ModelMap model) {
		String rtn = "/admin/users";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		List<User> users = userService.getAll();

		model.addAttribute("users", users);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "User");
		model.addAttribute("submenu", "users");

		return rtn;
	}


	@RequestMapping(value="/users/search", method=RequestMethod.GET)
	public String searchUsers(@RequestParam Map<String,String> requestParams, ModelMap model) {
		String rtn = "/admin/users";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String name = "%" + requestParams.get("name") + "%";
		List<User> users = userService.getByName(name);

		model.addAttribute("users", users);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "User");
		model.addAttribute("submenu", "users");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/user"})
	public String dspUser(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/user";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		List<Role> roles = roleService.getAll();

		List<String> genders = new ArrayList<String>();

		genders.add("Male");
		genders.add("Female");

		model.addAttribute("roles", roles);
		model.addAttribute("genders", genders);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "User");
		model.addAttribute("submenu", "users");

		return rtn;
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/user/{userid}"})
	public String updateUser(ModelMap model, @PathVariable("userid") Long userid, HttpSession httpSession) {
		String rtn = "admin/user";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		User user = userService.getById(userid);

		List<Role> roles = roleService.getAll();
		List<String> genders = new ArrayList<String>();

		genders.add("Male");
		genders.add("Female");

		model.addAttribute("user", user);
		model.addAttribute("roles", roles);
		model.addAttribute("genders", genders);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "User");
		model.addAttribute("submenu", "users");

		return rtn;
	}

	@RequestMapping(method={RequestMethod.POST}, value={"/user"})
	public String doUser(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "users";

		logger.info("doUser is call!!!!!");

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String userid = requestParams.get("userid");
		String username = requestParams.get("username");
		String firstname = requestParams.get("firstname");
		String lastname = requestParams.get("lastname");
		String gender = requestParams.get("gender");
		String email = requestParams.get("email");
		String phone = requestParams.get("phone");
		String cell = requestParams.get("cell");
		String password = requestParams.get("password");
		String roleid = requestParams.get("roleid");

		Calendar cal = Calendar.getInstance();

		User user = new User();

		Role role = roleService.getById(Long.parseLong(roleid));
		List<Userrole> userroles = new ArrayList<>();
		Userrole ur = new Userrole();

		if(userid != null && !userid.isEmpty()) {
			user = userService.getById(Long.parseLong(userid));
			userroles = user.getUserroles();
			ur = userroles.get(0);
			ur.setRole(role);
		}

		user.setUsername(username);
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setGender(gender);
		user.setEmail(email);
		user.setPhone(phone);
		user.setCell(cell);
		user.setUserroles(userroles);
		user.setCreatedby(loginUser);
		user.setCreationdate(cal);
		if(!password.isEmpty()) {
			user.setPassword(bCryptPasswordEncoder.encode(password));
		}

		user = userService.save(user);

		if(userroles.isEmpty()) {
			ur.setUserroleid(0);
			ur.setUser(user);
			ur.setRole(role);
			ur = userroleService.save(ur);
			userroles.add(ur);
		}

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "User");
		model.addAttribute("submenu", "users");

		return "redirect:" + rtn;
	}

}
