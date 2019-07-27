package com.vhc.controller.store.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

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
import com.vhc.controller.store.StoreBase;
import com.vhc.core.model.City;
import com.vhc.core.model.Giftcard;
import com.vhc.core.model.Status;
import com.vhc.core.model.Store;
import com.vhc.core.model.User;
import com.vhc.security.LoginUser;


@Controller
@RequestMapping({"/store/admin"})
public class StoreAdminGiftcard extends StoreBase {

	private final Logger logger = LoggerFactory.getLogger(StoreAdminGiftcard.class);

	/*@RequestMapping(method={RequestMethod.GET}, value={"/giftcards"})
	public String dspGiftcards(ModelMap model, HttpSession httpSession) {
		String rtn = "store/admin/giftcards";

		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);

		List<Giftcard> mfs = giftcardService.getAll();
		model.addAttribute("giftcards", mfs);
		model.addAttribute("loginUser", loginUser);

		return rtn;
	}*/


	@RequestMapping(method={RequestMethod.GET}, value={"/giftcard"})
	public String dspGiftcard(ModelMap model, HttpSession httpSession) {
		String rtn = "store/admin/giftcard";

		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);

		Store store = staffService.getByUser(loginUser).getStore();

		//List<City> cities = cityService.getAll();

		List<Store> stores = new ArrayList<>();//storeService.getAll();

		stores.add(store);
		List<Status> statuss = statusService.getByReftbl("giftcards");

		model.addAttribute("statuss", statuss);

		model.addAttribute("stores", stores);
		//model.addAttribute("cities", cities);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Sales");

		return rtn;
	}


	/*@RequestMapping(method={RequestMethod.GET}, value={"/giftcard/{giftcardid}"})
	public String updateGiftcard(ModelMap model, @PathVariable("giftcardid") Long giftcardid, HttpSession httpSession) {
		String rtn = "store/admin/giftcard";

		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);

		long mfid = giftcardid.longValue();
		Giftcard giftcard = giftcardService.getById(mfid);

		List<City> cities = cityService.getAll();
		model.addAttribute("giftcard", giftcard);
		model.addAttribute("cities", cities);
		model.addAttribute("loginUser", loginUser);

		return rtn;
	}*/


	@RequestMapping(method={RequestMethod.POST}, value={"/giftcard"})
	public String doGiftcard(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "giftcardbalance";

		logger.info("doGiftcard is call!!!!!");

		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);

		String cardid = requestParams.get("giftcardid");
		String code = requestParams.get("code");
		String pin = requestParams.get("pin");
		String amount = requestParams.get("amount");
		String balance = requestParams.get("balance");
		String storeid = requestParams.get("storeid");

		Giftcard giftcard = new Giftcard();
		if(cardid != null && !cardid.isEmpty()) {
			giftcard.setGiftcardid(Long.parseLong(cardid));
		}

		if(storeid != null && !storeid.isEmpty()) {
			Store store = storeService.getById(Long.parseLong(storeid));
			giftcard.setStore(store);
		}

		giftcard.setCode(code);
		giftcard.setPin(pin);;
		giftcard.setLoaddate(Calendar.getInstance());
		giftcard.setLoadedby(loginUser);

		if(amount != null && !amount.isEmpty()) {
			giftcard.setAmount(new BigDecimal(amount));
			if(cardid == null || cardid.isEmpty()) {
				giftcard.setBalance(new BigDecimal(amount));
			}
		}
		if(balance != null && !balance.isEmpty()) {
			giftcard.setBalance(new BigDecimal(balance));
		}

		giftcardService.save(giftcard);

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Sales");

		return "redirect:" + rtn;
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
