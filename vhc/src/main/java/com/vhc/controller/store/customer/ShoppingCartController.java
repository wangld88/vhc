package com.vhc.controller.store.customer;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vhc.controller.store.StoreBase;
import com.vhc.dto.ShoppingCart;
import com.vhc.model.Customer;
import com.vhc.model.Item;
import com.vhc.model.Status;
import com.vhc.model.Store;
import com.vhc.model.User;
import com.vhc.security.LoginUser;


@Controller
@RequestMapping({"/store/customer/cart"})
public class ShoppingCartController extends StoreBase {

	private final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);


	@RequestMapping(value={"/",""}, method = RequestMethod.GET)
	public String dspCart() {
		String rtn = "";

		logger.info("Display shopping cart");

		return rtn;
	}


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listCart(HttpServletRequest request, ModelMap model, HttpSession session) {
		String cartKey = getCartKey(request);

		ShoppingCart cart = (ShoppingCart) session.getAttribute(cartKey);

		if(cart != null) {
			cart = new ShoppingCart();
			session.setAttribute(cartKey, cart);
		}

		model.addAttribute("cart", cart);

		return "";
	}


	@RequestMapping(value = "/add/{sku}", method = RequestMethod.GET)
	public String buy(@PathVariable("sku") String sku, HttpServletRequest request, HttpSession session) {

		String cartKey = getCartKey(request);

		Customer customer = customerService.getByUser(getLoginUser(getPrincipal()));
		ShoppingCart cart = new ShoppingCart();
		List<Item> items = new ArrayList<Item>();
		long storeid = 1;
		//Store store = staffService.getByUser(loginUser).getStore();
		Status received = statusService.getByName("Received");

		if (session.getAttribute(cartKey) != null) {
			cart = (ShoppingCart) session.getAttribute(cartKey);
			items = cart.getItems();
		}

		items.add(inventoryService.getByStoreAvaiableUPC(sku, storeid, received).get(0).getItem());
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

		ShoppingCart cart = cart = (ShoppingCart) session.getAttribute(cartKey);

		List<Item> items = cart.getItems();
		
		items.removeIf(i -> i.getSku().equals(sku));
		cart.setItems(items);

		session.setAttribute(cartKey, cart);

		return "redirect:/cart/index";
	}


	@RequestMapping(value = "/checkout/address", method = RequestMethod.GET)
	public String provideAddress() {

		String rtn = "";

		logger.info("Display shopping cart");

		return rtn;
	}


	@RequestMapping(value = "/checkout/payment", method = RequestMethod.GET)
	public String makePayment() {

		String rtn = "";

		logger.info("Display shopping cart");

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
            user = userService.findByUsername("");
        }

        return user;
    }

}
