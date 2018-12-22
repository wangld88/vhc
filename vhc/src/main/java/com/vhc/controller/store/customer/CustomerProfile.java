package com.vhc.controller.store.customer;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vhc.controller.BaseController;
import com.vhc.dto.ShopItem;
import com.vhc.dto.ShoppingCart;
import com.vhc.model.Account;
import com.vhc.model.Address;
import com.vhc.model.City;
import com.vhc.model.Customer;
import com.vhc.model.Role;
import com.vhc.model.Status;
import com.vhc.model.User;
import com.vhc.model.Userrole;
import com.vhc.security.LoginUser;


@Controller
@RequestMapping({"/customer"})
public class CustomerProfile extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerProfile.class);

	@RequestMapping(method={RequestMethod.GET}, value={"/login/signup"})
	public String dspSignup(ModelMap model) {

		List<Role> roles = roleService.getAll();

		List<String> genders = new ArrayList<String>();

		genders.add("Male");
		genders.add("Female");

		List<City> cities = cityService.getAll();

		model.addAttribute("cities", cities);
		model.addAttribute("roles", roles);
		model.addAttribute("genders", genders);

		return "customer/login/signup";
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/login/signup"})
	public String doSignup(@RequestParam Map<String,String> requestParams,
			ModelMap model,
			HttpServletRequest request,
			HttpServletResponse response) {

		try {
			logger.info("Customer doSignup");
			// create user
			String username = requestParams.get("username");
			String firstname = requestParams.get("firstname");
			String lastname = requestParams.get("lastname");
			String gender = requestParams.get("gender");
			String email = requestParams.get("email");
			String phone = requestParams.get("phone");
			String cell = requestParams.get("cell");
			String password = requestParams.get("password");
			String confpswd = requestParams.get("confpswd");
			String street = requestParams.get("street");
			String cityid = requestParams.get("cityid");
			String postalcode = requestParams.get("postalcode");
			Calendar cal = Calendar.getInstance();

			if(password != null && !password.isEmpty() && !password.equals(confpswd)) {
				return "customer/login/signup";
			}

			User user = new User();

			//List<Userrole> userroles = new ArrayList<>();

			user.setUsername(username);
			user.setFirstname(firstname);
			user.setLastname(lastname);
			user.setGender(gender);
			user.setEmail(username);
			user.setPhone(phone);
			user.setCell(cell);
			user.setCreationdate(cal);

			if(!password.isEmpty()) {
				user.setPassword(bCryptPasswordEncoder.encode(password));
			}

			user = userService.save(user);

			user.setCreatedby(user);
			user = userService.save(user);

			user = userService.getById(user.getUserid());

			Role role = roleService.getByName("CUSTOMER");
			Userrole ur = new Userrole();
			ur.setUser(user);
			ur.setRole(role);
			ur = userroleService.save(ur);

			// create address
			Address ads = new Address();
			City city = cityService.getById(Long.parseLong(cityid));
			ads.setCity(city);
			ads.setStreet(street);
			ads.setPostalcode(postalcode);

			ads = addressService.save(ads);

			// create customer
			Customer customer = new Customer();

			customer.setAddress(ads);
			customer.setUser(user);
			//customer.setComments(comments);
			customer.setCreatedby(user);
			customer.setCreationdate(cal);
			customer.setUpdatedate(cal);
			customer.setUpdatedby(user);

			customer = customerService.save(customer);

			// create account
			Account acct = new Account();
			Status status = statusService.getByName("Active");

			acct.setCustomer(customer);
			acct.setStatus(status);
			acct.setCreatedby(user);
			acct.setCreationdate(cal);

			acct = accountService.save(acct);

		} catch(Exception e) {
			e.printStackTrace();
			return "customer/login/sign";
		}

		return "customer/home";
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/home"})
	public String dspHome(ModelMap model, HttpServletRequest request, HttpSession session) {

		logger.info("dspHome is called in Profile");
		Object principal = getPrincipal();

		if(!isCustomer(principal)) {
			return "redirect:/customer/login/logout";
		}

		User loginUser = getLoginUser(principal);

		model.addAttribute("loginUser", loginUser);

		String cartKey = getCartKey(request);

		if (session.getAttribute(cartKey) != null) {
			return "redirect:/store/customer/shoppingcart";
		}
		return "customer/home";
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/profile"})
	public String dspProfile(ModelMap model) {

		Object principal = getPrincipal();

		if(!isCustomer(principal)) {
			return "redirect:/customer/login/logout";
		}

		User loginUser = getLoginUser(principal);

		Customer customer = customerService.getByUser(loginUser);
		List<String> genders = new ArrayList<String>();

		genders.add("Male");
		genders.add("Female");

		List<City> cities = cityService.getAll();

		model.addAttribute("cities", cities);
		model.addAttribute("customer", customer);
		model.addAttribute("genders", genders);
		model.addAttribute("loginUser", loginUser);

		return "customer/profile";
	}

	@RequestMapping(method={RequestMethod.POST}, value={"/profile"})
	public String doProfile(@RequestParam Map<String,String> requestParams, ModelMap model) {

		Object principal = getPrincipal();

		if(!isCustomer(principal)) {
			return "redirect:/customer/login/logout";
		}

		User loginUser = getLoginUser(principal);

		logger.info("Logged in user: " + loginUser.toString());

		Customer customer = customerService.getByUser(loginUser);
		List<String> genders = new ArrayList<String>();

		genders.add("Male");
		genders.add("Female");

		List<City> cities = cityService.getAll();

		String username = requestParams.get("username");
		String firstname = requestParams.get("firstname");
		String lastname = requestParams.get("lastname");
		String gender = requestParams.get("gender");
		//String email = requestParams.get("username");
		String phone = requestParams.get("phone");
		String cell = requestParams.get("cell");
		String password = requestParams.get("password");
		String confpswd = requestParams.get("confpswd");
		String street = requestParams.get("street");
		String cityid = requestParams.get("cityid");
		String postalcode = requestParams.get("postalcode");
		Calendar cal = Calendar.getInstance();

		loginUser.setUsername(username);
		loginUser.setEmail(username);

		//loginUser.setGender(gender);
		if(firstname != null && !firstname.isEmpty()) {
			loginUser.setFirstname(firstname);
		}
		if(lastname != null && !lastname.isEmpty()) {
			loginUser.setLastname(lastname);
		}
		if(phone != null && !phone.isEmpty()) {
			loginUser.setPhone(phone);
		}
		if(cell != null && !cell.isEmpty()) {
			loginUser.setCell(cell);
		}

		loginUser.setUpdatedate(cal);

		userService.save(loginUser);

		Address address = customer.getAddress();

		if(cityid != null && Long.parseLong(cityid) != address.getCity().getCityid()) {
			City city = cityService.getById(Long.parseLong(cityid));
			address.setCity(city);
		}

		if(street != null && !street.isEmpty()) {
			address.setStreet(street);
		}

		if(postalcode != null && !postalcode.isEmpty()) {
			address.setPostalcode(postalcode);
		}

		address = addressService.save(address);

		//customer = customerService.getByUser(loginUser).get(0);

		model.addAttribute("cities", cities);
		model.addAttribute("customer", customer);
		model.addAttribute("genders", genders);
		model.addAttribute("loginUser", loginUser);

		return "customer/home";
	}

	private Object getPrincipal() {

		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	private User getLoginUser(Object principal) {

		User user = null;

        if (principal instanceof LoginUser) {
            user = ((LoginUser)principal).getUser();
        } else {
            user = userService.findByUsername("");
        }

        return user;
    }

	private boolean isCustomer(Object principal) {

		if(principal instanceof LoginUser) {
			return ((LoginUser) principal).getAuthorities().contains(new SimpleGrantedAuthority("CUSTOMER"));
		} else {
			return false;
		}
	}

	private String getCartKey(HttpServletRequest request) {
		String ip = getUserIP(request);
		String cartKey = hash(ip + "-cartkey");

		return cartKey;
	}

	private String getUserIP(HttpServletRequest request) {
		String ip = "";

		if(request != null) {
			ip = request.getHeader("X-FORWARDED-FOR");
			if(ip == null || ip.isEmpty()) {
				ip = request.getRemoteAddr();
			}
		}

		return ip;
	}

	private String hash(String source) {
		StringBuilder sb = new StringBuilder();

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashInBytes = md.digest(source.getBytes(StandardCharsets.UTF_8));

			for (byte b : hashInBytes) {
	            sb.append(String.format("%02x", b));
	        }
		} catch(Exception e) {
			logger.error("Exception is caught on generating shopping cart key: {}", e.getMessage());
		}

        return sb.toString();
	}
}
