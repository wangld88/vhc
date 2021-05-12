package com.vhc.controller.admin;

import java.util.ArrayList;
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

import com.vhc.core.model.Address;
import com.vhc.core.model.City;
import com.vhc.core.model.Staff;
import com.vhc.core.model.Store;
import com.vhc.core.model.User;


@Controller
@RequestMapping({"/admin"})
public class AdminStore extends AdminBase {

	private final Logger logger = LoggerFactory.getLogger(AdminStore.class);

	@RequestMapping(method={RequestMethod.GET}, value={"/stores"})
	public String dspStores(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/stores";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		List<Store> mfs = storeService.getAll();
		model.addAttribute("stores", mfs);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/store"})
	public String dspStore(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/store";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		List<City> cities = cityService.getAll();

		model.addAttribute("cities", cities);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/store/{storeid}"})
	public String updateStore(ModelMap model, @PathVariable("storeid") Long storeid, HttpSession httpSession) {
		String rtn = "admin/store";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		long mfid = storeid.longValue();
		Store store = storeService.getById(mfid);

		List<City> cities = cityService.getAll();
		model.addAttribute("store", store);
		model.addAttribute("cities", cities);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/store"})
	public String doStore(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "stores";

		logger.info("doStore is call!!!!!");

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		Address ads = new Address();

		String mfid = requestParams.get("storeid");
		String street = requestParams.get("street");
		String cityid = requestParams.get("cityid");
		String postalcode = requestParams.get("postalcode");
		String name = requestParams.get("name");
		String code = requestParams.get("code");
		String contact = requestParams.get("contact");
		String phone = requestParams.get("phone");
		String mobile = requestParams.get("mobile");
		String email = requestParams.get("email");
		String website = requestParams.get("website");
		String facebook = requestParams.get("facebook");
		String google = requestParams.get("google");
		String twitter = requestParams.get("twitter");
		String comments = requestParams.get("comments");

		logger.info("cityid: " + cityid);
		City city = cityService.getById(Long.parseLong(cityid));
		ads.setCity(city);
		ads.setStreet(street);
		ads.setPostalcode(postalcode);

		addressService.save(ads);

		Store store = new Store();
		if(mfid != null && !mfid.isEmpty()) {
			store.setStoreid(Long.parseLong(mfid));
		}
		store.setName(name);
		store.setCode(code);
		store.setEmail(email);
		store.setAddress(ads);
		store.setContact(contact);
		store.setMobile(mobile);
		store.setPhone(phone);
		store.setWebsite(website);
		store.setFacebook(facebook);
		store.setGoogle(google);
		store.setTwitter(twitter);
		store.setComments(comments);
		storeService.save(store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");

		return "redirect:" + rtn;
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/staffs"})
	public String dspStaffs(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/staffs";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		List<Staff> staffs = staffService.getAll();
		model.addAttribute("staffs", staffs);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/staff"})
	public String dspStaff(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/staff";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		List<String> roles = new ArrayList<String>();
		roles.add("ADMIN");
		roles.add("STAFF");

		List<Store> stores = storeService.getAll();
		List<User> users = userService.getByRolenames(roles);

		model.addAttribute("users", users);
		model.addAttribute("stores", stores);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/staff/{staffid}"})
	public String updateStaff(ModelMap model, @PathVariable("staffid") Long staffid, HttpSession httpSession) {
		String rtn = "admin/staff";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		long mfid = staffid.longValue();
		Staff staff = staffService.getById(mfid);
		List<User> users = new ArrayList<User>();
		users.add(staff.getUser());

		List<Store> stores = storeService.getAll();
		model.addAttribute("staff", staff);
		model.addAttribute("users", users);
		model.addAttribute("stores", stores);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/staff"})
	public String doStaff(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "staffs";

		logger.info("doStaff is call!!!!!");

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		Staff staff = new Staff();

		String staffid = requestParams.get("staffid");
		String storeid = requestParams.get("storeid");
		String userid = requestParams.get("userid");

		Store store = storeService.getById(Long.parseLong(storeid));
		User user = userService.getById(Long.parseLong(userid));

		if(staffid != null && !staffid.isEmpty()) {
			staff.setStaffid(Long.parseLong(staffid));
		}
		staff.setStore(store);
		staff.setUser(user);

		staffService.save(staff);

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");

		return "redirect:" + rtn;
	}

}
