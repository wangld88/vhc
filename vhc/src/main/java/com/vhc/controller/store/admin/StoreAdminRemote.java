package com.vhc.controller.store.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vhc.controller.store.StoreBase;
import com.vhc.dto.OrderDTO;
import com.vhc.core.model.Category;
import com.vhc.core.model.Customer;
import com.vhc.core.model.Giftcard;
import com.vhc.core.model.Order;
import com.vhc.core.model.Product;
import com.vhc.core.model.Promocode;
import com.vhc.core.model.Size;
import com.vhc.core.model.User;
import com.vhc.util.Message;


@RestController
@RequestMapping("/store/admin")
public class StoreAdminRemote extends StoreBase {

	Logger logger = LoggerFactory.getLogger(StoreAdminRemote.class);

	@RequestMapping(method=RequestMethod.POST, value={"/products","/item/products","/category/products"})
	public List<Product> getProducts(ModelMap model, @RequestParam("name") String name, HttpSession httpSession) {
		logger.info("product search is called");

		List<Product> products = productService.getByName("%" + name + "%");

		logger.info("Product Search Result: " + products.size());
		for(Product p : products) {
			p.setImages(new ArrayList<>());
		}

		return products;
	}

	@RequestMapping(method=RequestMethod.POST, value={"/size","/item/size"})
	public List<Size> getSizes(ModelMap model, @RequestParam("typeid") String typeid,  @RequestParam("size") String size, HttpSession httpSession) {
		logger.info("Size autocomplete search is called");

		List<Size> sizes = new ArrayList<>();

		/*if(typeid == null || typeid.trim().isEmpty()) {
			sizes = sizeService.getAll();
		}*/
		if(typeid != null && !typeid.trim().isEmpty()) {
			sizes = sizeService.getByTypeAndSizenum(Long.parseLong(typeid), size + "%");
		} else {
			sizes = sizeService.getBySizenum(size + "%");
		}

		logger.info("Size autocomplete Search Result: " + sizes.size());

		return sizes;
	}


	@RequestMapping(method=RequestMethod.POST, value="/searchCustomers")
	public List<Customer> getCustomers(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		logger.info("AJAX Customer search is called");

		String lastname = requestParams.get("lastname");
		//String phone = requestParams.get("phone");

		//Message msg = new Message();
		List<Customer> customers = new ArrayList<>();

		if(lastname == null || lastname.isEmpty()) {
			//msg.setStatus(Message.ERROR);
			//msg.setMessage("Please enter search criteria to narrow down the search!");
			customers = customerService.getAll();
		} else {
			User user = new User();
			user.setLastname(lastname);
			//user.setPhone(phone);

			customers = customerService.getByLastnamePhone(user);
		}
		System.out.println("customers: "+customers.size());
		logger.debug("customers: "+customers.size());
		//model.addAttribute("customers", customers);
		//model.addAttribute("message", msg);

		logger.info("Size autocomplete Search Result: " + customers.size());

		return customers;
	}


	@RequestMapping(method=RequestMethod.GET, value="/customerHistory/{customerid}")
	public List<OrderDTO> getCustomerHistory(@PathVariable Long customerid, ModelMap model, HttpSession httpSession) {

		List<Order> orders = orderService.getByCustomer(customerid);

		return null;//orders.stream().forEach(action);;
	}

	@RequestMapping(method=RequestMethod.POST, value="/promocode")
	public long getPromotion(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		long rtn = 0;

		String code = requestParams.get("promocode");
		System.out.println("Code: "+code);
		Promocode p = promocodeService.getByCode(code);
		Calendar cal = Calendar.getInstance();

		if(p != null && cal.after(p.getStartdate()) && cal.before(p.getEnddate())) {
			rtn = p.getPercentage();
		}
		return rtn;
	}


	@RequestMapping(method=RequestMethod.POST, value="/checkgiftcard")
	public BigDecimal checkBalance(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {

		String cardnum = requestParams.get("cardcode");
		String pin = requestParams.get("pin");

		Giftcard giftcard = null;

		if(pin != null && !pin.isEmpty()) {
			giftcard = giftcardService.getByCodePin(cardnum, pin);
		} else {
			giftcard = giftcardService.getByCode(cardnum);
		}

		model.addAttribute("giftcard", giftcard);

		logger.info("Gift card number {}, pin {}, content {}",cardnum, pin, giftcard);

		if(giftcard == null) {
			return BigDecimal.ZERO;
		} else {
			return giftcard.getBalance();
		}

	}


	/*@RequestMapping(method=RequestMethod.POST, value="/categoryImage")
	public Category addImage(ModelMap model, @RequestParam("picture") MultipartFile picture, HttpSession httpSession) {

	}*/
}
