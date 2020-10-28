package com.vhc.controller.store.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vhc.controller.store.StoreBase;
import com.vhc.core.model.Address;
import com.vhc.core.model.City;
import com.vhc.core.model.Customer;
import com.vhc.core.model.Role;
import com.vhc.core.model.User;
import com.vhc.core.model.Userrole;
import com.vhc.security.LoginUser;
import com.vhc.util.Message;

@Controller
@RequestMapping("/store/admin")
public class StoreAdminUser extends StoreBase {

	private static final Logger logger = LoggerFactory.getLogger(StoreAdminUser.class);


	@RequestMapping("/findCustomer")
	public String dspFindCustomer() {
		return "store/admin/popup";
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/searchCustomer"})
	public String findCustomers(@RequestParam Map<String,String> requestParams,
								ModelMap model,
								HttpSession httpSession) {
		String lastname = requestParams.get("lastname");
		String phone = requestParams.get("phone");

		Message msg = new Message();
		List<Customer> customers = new ArrayList<>();

		if((lastname == null || lastname.isEmpty()) && (phone == null || phone.isEmpty())) {
			msg.setStatus(Message.ERROR);
			msg.setMessage("Please enter search criteria to narrow down the search!");
		} else {
			User user = new User();
			user.setLastname(lastname);
			user.setPhone(phone);

			customers = customerService.getByLastnamePhone(user);
		}
		//System.out.println("customers: "+customers.size());
		logger.debug("customers: "+customers.size());
		model.addAttribute("customers", customers);
		model.addAttribute("message", msg);
		return "store/admin/popup";
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/searchCustomer/{customerid}"})
	public String findCustomer(@RequestParam Map<String,String> requestParams,
								ModelMap model,
								@PathVariable("customerid") Long customerid,
								RedirectAttributes attributes,
								HttpSession httpSession) {

		String rtn = "store/admin/sales";
		Customer customer = customerService.getById(customerid);
		attributes.addFlashAttribute("customer", customer);
		model.addAttribute("customer", customer);

		return rtn;
	}


	@RequestMapping("/addCustomer")
	public String addCustomer(ModelMap model,
							HttpSession httpSession) {

		List<Role> roles = roleService.getAll();

		List<String> genders = new ArrayList<String>();

		genders.add("Male");
		genders.add("Female");

		List<City> cities = cityService.getAll();

		model.addAttribute("cities", cities);
		model.addAttribute("roles", roles);
		model.addAttribute("genders", genders);

		return "store/admin/addCustomer";
	}

	@RequestMapping(method={RequestMethod.POST}, value={"/addCustomer1"})
	public String doCustomer1(@RequestParam Map<String,String> requestParams,
						ModelMap model,
						RedirectAttributes attributes,
						HttpSession httpSession) {
		String rtn = "sales";

		logger.info("doUser is call!!!!!");

		if(!isStoreAdmin()) {
			return "redirect:/store/admin/logout";
		}

		String userid = requestParams.get("userid");
		String username = requestParams.get("username");
		String firstname = requestParams.get("firstname");
		String lastname = requestParams.get("lastname");
		String gender = requestParams.get("gender");
		String email = requestParams.get("email");
		String phone = requestParams.get("phone");
		String cell = requestParams.get("cell");
		//String password = requestParams.get("password");
		String roleid = requestParams.get("roleid");
		String street = requestParams.get("street");
		String cityid = requestParams.get("cityid");
		String postalcode = requestParams.get("postalcode");
		String comments = requestParams.get("comments");

		Address ads = new Address();

		logger.info("cityid: " + cityid);
		City city = cityService.getById(Long.parseLong(cityid));
		ads.setCity(city);
		ads.setStreet(street);
		ads.setPostalcode(postalcode);

		ads = addressService.save(ads);

		Calendar cal = Calendar.getInstance();

		User user = new User();

		Role role = roleService.getById(Long.parseLong(roleid));
		List<Userrole> userroles = new ArrayList<>();

		if(userid != null && !userid.isEmpty()) {
			user = userService.getById(Long.parseLong(userid));
			userroles = user.getUserroles();
		}

		user.setUsername(username);
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setGender(gender);
		user.setEmail(email);
		user.setPhone(phone);
		user.setCell(cell);
		user.setUserroles(userroles);
		user.setCreatedby(getPrincipal());
		user.setCreationdate(cal);
		//user.setPassword(bCryptPasswordEncoder.encode(password));

		user = userService.save(user);

		Userrole ur = new Userrole();
		if(!userroles.isEmpty()) {
			logger.info("ssssssssssssssssss");
			ur = userroles.get(0);
			ur.setRole(role);
		} else {
			logger.info("dddddddddddddddddddddd");
			ur.setUserroleid(0);
			ur.setUser(user);
			ur.setRole(role);
			ur = userroleService.save(ur);
			userroles.add(ur);
		}

		Customer customer = new Customer();

		customer.setAddress(ads);
		customer.setUser(user);
		customer.setComments(comments);
		customer.setCreatedby(getPrincipal());
		customer.setCreationdate(cal);
		customer.setUpdatedate(cal);
		customer.setUpdatedby(getPrincipal());

		customer = customerService.save(customer);

		model.addAttribute("loginUser", getPrincipal());

		attributes.addFlashAttribute("customer", customer);

		return "redirect:" + "searchCustomer/" + customer.getCustomerid();
	}

	private boolean isStoreAdmin() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if(principal instanceof LoginUser) {
			return ((LoginUser) principal).getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
		} else {
			return false;
		}
	}

	private User getPrincipal(){
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
    }
}
