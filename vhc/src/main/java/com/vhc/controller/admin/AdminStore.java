package com.vhc.controller.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vhc.controller.BaseController;
import com.vhc.model.Address;
import com.vhc.model.City;
import com.vhc.model.Store;
import com.vhc.model.User;
import com.vhc.security.LoginUser;


@Controller
@RequestMapping({"/admin"})
public class AdminStore extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(AdminStore.class);
	
	@RequestMapping(method={RequestMethod.GET}, value={"/stores"})
	public String dspStores(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/stores";
		
		List<Store> mfs = storeService.getAll();
		model.addAttribute("stores", mfs);
		model.addAttribute("loginUser", getPrincipal());
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/store"})
	public String dspStore(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/store";
		
		List<City> cities = cityService.getAll();
		
		model.addAttribute("cities", cities);
		model.addAttribute("loginUser", getPrincipal());
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/store/{storeid}"})
	public String updateStore(ModelMap model, @PathVariable("storeid") Long storeid, HttpSession httpSession) {
		String rtn = "admin/store";
		
		long mfid = storeid.longValue();
		Store store = storeService.getById(mfid);
		
		List<City> cities = cityService.getAll();
		model.addAttribute("store", store);
		model.addAttribute("cities", cities);
		model.addAttribute("loginUser", getPrincipal());
		
		return rtn;
	}

	
	@RequestMapping(method={RequestMethod.POST}, value={"/store"})
	public String doStore(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "stores";
		
		logger.info("doStore is call!!!!!");
		
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
		model.addAttribute("loginUser", getPrincipal());
		
		return "redirect: " + rtn;
	}

	private User getPrincipal(){
    	User user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //System.out.println("Role is : "+((LoginStudent)principal).toString());
        if (principal instanceof LoginUser) {
            user = ((LoginUser)principal).getUser();
        } else {
            user = userService.findByUsername("");
        }
        return user;
    }
	
	
}
