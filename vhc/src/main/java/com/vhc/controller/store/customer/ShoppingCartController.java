package com.vhc.controller.store.customer;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vhc.controller.store.StoreBase;
import com.vhc.dto.ItemForm;
import com.vhc.dto.ShopItem;
import com.vhc.dto.ShoppingCart;
import com.vhc.core.model.Customer;
import com.vhc.core.model.Item;
import com.vhc.core.model.Status;
import com.vhc.core.model.Store;
import com.vhc.core.model.User;
import com.vhc.security.LoginUser;


@Controller
@RequestMapping({"/store/customer/cart"})
public class ShoppingCartController extends StoreBase {

	private final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);


	@RequestMapping(value={"/",""}, method = RequestMethod.GET)
	public String dspCart(ModelMap model, HttpServletRequest request, HttpSession session) {
		String rtn = "/customer/shoppingcart";

		logger.info("dspCart is called in ShoppingCartController");
		Object principal = getPrincipal();

		if(!isCustomer(principal)) {
			return "redirect:/customer/login";
		}

		User loginUser = getLoginUser(principal);
		model.addAttribute("loginUser", loginUser);

		Customer customer = customerService.getByUser(getLoginUser(getPrincipal()));
		//User loginUser = getLoginUser(principal);
		logger.info("Display shopping cart");

		String cartKey = getCartKey(request);
		ShoppingCart cart = new ShoppingCart();
		List<ShopItem> items = new ArrayList<ShopItem>();
		double total = 0;

		if (session.getAttribute(cartKey) != null) {
			cart = (ShoppingCart) session.getAttribute(cartKey);
			items = cart.getItems();
			total = items.stream().collect(Collectors.summarizingDouble(ShopItem::getPrice)).getSum();
		}

		model.addAttribute("total", total);
		model.addAttribute("items", items);
		model.addAttribute("customer", customer);

		return rtn;
	}


	@RequestMapping(value = "/add/{sku}", method = RequestMethod.GET)
	public String buy(@PathVariable("sku") String sku, HttpServletRequest request, HttpSession session) {

		String cartKey = getCartKey(request);

		Customer customer = customerService.getByUser(getLoginUser(getPrincipal()));
		ShoppingCart cart = new ShoppingCart();
		List<ShopItem> items = new ArrayList<ShopItem>();
		long storeid = 1;
		//Store store = staffService.getByUser(loginUser).getStore();
		Status received = statusService.getByName("Received");

		if (session.getAttribute(cartKey) != null) {
			cart = (ShoppingCart) session.getAttribute(cartKey);
			items = cart.getItems();
		}

		items.add(new ShopItem(inventoryService.getByStoreAvaiableUPC(sku, storeid, received).get(0).getItem()));
		if(customer != null) {
			cart.setCustomer(customer);
		}
		cart.setItems(items);

		session.setAttribute(cartKey, cart);

		return "redirect:/cart/index";
	}

	@RequestMapping(value = "/remove/{sku}", method = RequestMethod.GET)
	public String remove(@PathVariable("sku") String sku, HttpServletRequest request, HttpSession session) {

		String cartKey = getCartKey(request);

		Customer customer = customerService.getByUser(getLoginUser(getPrincipal()));

		ShoppingCart cart = (ShoppingCart) session.getAttribute(cartKey);

		List<ShopItem> items = cart.getItems();

		items.removeIf(i -> i.getSku().equals(sku));
		cart.setItems(items);

		session.setAttribute(cartKey, cart);

		return "redirect:/cart/index";
	}


	@RequestMapping(value = "/shipping", method = RequestMethod.POST)
	public String provideAddress(ModelMap model, HttpServletRequest request, HttpSession session) {

		String rtn = "/customer/shipping";
		Object principal = getPrincipal();

		if(!isCustomer(principal)) {
			return "redirect:/customer/login";
		}

		User loginUser = getLoginUser(principal);
		model.addAttribute("loginUser", loginUser);

		Customer customer = customerService.getByUser(getLoginUser(getPrincipal()));

		logger.info("Display provideAddress");

		return rtn;
	}


	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public String makePayment(ModelMap model, HttpServletRequest request, HttpSession session) {

		String rtn = "/customer/payment";
		Object principal = getPrincipal();

		if(!isCustomer(principal)) {
			return "redirect:/customer/login";
		}

		User loginUser = getLoginUser(principal);
		model.addAttribute("loginUser", loginUser);

		Customer customer = customerService.getByUser(getLoginUser(getPrincipal()));

		logger.info("Display makePayment");

		return rtn;
	}


	@RequestMapping(value = "/review", method = RequestMethod.POST)
	public String reviewOrder(ModelMap model, HttpServletRequest request, HttpSession session) {

		String rtn = "/customer/review";
		Object principal = getPrincipal();

		if(!isCustomer(principal)) {
			return "redirect:/customer/login";
		}

		User loginUser = getLoginUser(principal);
		model.addAttribute("loginUser", loginUser);

		Customer customer = customerService.getByUser(getLoginUser(getPrincipal()));

		logger.info("Display shopping cart");

		return rtn;
	}


	@RequestMapping(value = "/invoice", method = RequestMethod.POST)
	public String completeTxn(ModelMap model, HttpServletRequest request, HttpSession session) {

		String rtn = "/customer/invoice";
		Object principal = getPrincipal();

		if(!isCustomer(principal)) {
			return "redirect:/customer/login";
		}

		User loginUser = getLoginUser(principal);
		model.addAttribute("loginUser", loginUser);

		Customer customer = customerService.getByUser(getLoginUser(getPrincipal()));

		logger.info("Display shopping cart invoice");

		return rtn;
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

	private boolean isCustomer(Object principal) {

		if(principal instanceof LoginUser) {
			return ((LoginUser) principal).getAuthorities().contains(new SimpleGrantedAuthority("CUSTOMER"));
		} else {
			return false;
		}
	}
}
