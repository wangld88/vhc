package com.vhc.controller.admin;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vhc.core.model.Brand;
import com.vhc.core.model.Event;
import com.vhc.core.model.Eventproduct;
import com.vhc.core.model.Product;
import com.vhc.core.model.Status;
import com.vhc.core.model.Style;
import com.vhc.core.model.Type;
import com.vhc.core.model.User;


/**
 *
 * @author K & J Consulting
 *
 */
@Controller
@RequestMapping({"/admin"})
public class AdminBusiness extends AdminBase {

	private final Logger logger = LoggerFactory.getLogger(AdminGiftcard.class);


	@GetMapping("/events")
	public String dspEvents(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/events";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		List<Event> events = eventService.getAll();

		model.addAttribute("events", events);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");
		model.addAttribute("submenu", "giftcards");

		return rtn;
	}

	@GetMapping("/event")
	public String dspNewEvent(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/event";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		logger.info("!!!!! dspEvent is called");

		Event event = new Event();

		List<Status> statuss = statusService.getByReftbl("general");

		model.addAttribute("event", event);
		model.addAttribute("statuss", statuss);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");
		model.addAttribute("submenu", "giftcards");

		return rtn;
	}

	@GetMapping("/event/{eventid}")
	public String dspEvent(@PathVariable("eventid") Long eventid,
			ModelMap model, HttpSession httpSession) {
		String rtn = "admin/event";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		logger.info("!!!!! dspEvent is called");

		Event event = eventService.getById(eventid);

		List<Status> statuss = statusService.getByReftbl("general");
		List<Type> types = typeService.getAll();
		List<Brand> brands = brandService.getAll();
		List<Style> styles = styleService.getAll();

		List<Eventproduct> products = eventproductService.getByEventid(eventid);

		model.addAttribute("products", products);
		model.addAttribute("styles", styles);
		model.addAttribute("types", types);
		model.addAttribute("brands", brands);
		model.addAttribute("event", event);
		model.addAttribute("statuss", statuss);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");
		model.addAttribute("submenu", "giftcards");

		return rtn;
	}

	@GetMapping("/event/{eventid}/products")
	public String dspEventProduct(@PathVariable("eventid") Long eventid,
			@RequestParam Map<String,String> requestParams,
			ModelMap model,
			HttpSession httpSession) {

		String rtn = "admin/event";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String brandid = requestParams.get("brandid");
		String typeid = requestParams.get("typeid");
		String styleid = requestParams.get("styleid");

		Event event = eventService.getById(eventid);

		List<Status> statuss = statusService.getByReftbl("general");

		List<Eventproduct> eproducts = eventproductService.getByEventid(eventid);

		List<Product> products = productService.getByIds(Long.parseLong(brandid),Long.parseLong(typeid),Long.parseLong(styleid));

		for(Product p : products) {
			Eventproduct ep = new Eventproduct();
			ep.setProduct(p);
			eproducts.add(ep);
		}

		List<Type> types = typeService.getAll();
		List<Brand> brands = brandService.getAll();
		List<Style> styles = styleService.getAll();

		model.addAttribute("styles", styles);
		model.addAttribute("types", types);
		model.addAttribute("brands", brands);
		model.addAttribute("event", event);
		model.addAttribute("statuss", statuss);
		model.addAttribute("products", eproducts);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");
		model.addAttribute("submenu", "giftcards");

		return rtn;
	}


	@PostMapping("/event")
	public String doEvent(@RequestParam Map<String,String> requestParams,
			ModelMap model,
			HttpSession httpSession)
			throws Exception {

		String rtn = "admin/event";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String evtid = requestParams.get("eventid");
		String name = requestParams.get("name");
		String description = requestParams.get("description");
		String startdate = requestParams.get("startdate");
		String enddate = requestParams.get("enddate");
		String statusid = requestParams.get("statusid");

		Status status = statusService.getById(Long.parseLong(statusid));

		Calendar today = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(formatter.parse(startdate));
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(formatter.parse(enddate));

		Event event = new Event();

		if(evtid != null && !evtid.isEmpty()) {
			event = eventService.getById(Long.parseLong(evtid));
			if(event == null) {
				event = new Event();
			}
		}

		event.setName(name);
		event.setDescription(description);
		event.setStartdate(startCal);
		event.setEnddate(endCal);
		event.setCreatedby(loginUser);
		event.setCreationdate(today);
		event.setStatus(status);

		event = eventService.save(event);

		List<Status> statuss = statusService.getByReftbl("general");

		List<Type> types = typeService.getAll();
		List<Brand> brands = brandService.getAll();
		List<Style> styles = styleService.getAll();

		List<Eventproduct> products = eventproductService.getByEventid(event.getEventid());

		if(!products.isEmpty()) {
			Calendar endday = (Calendar) event.getEnddate().clone();
			endday.add(Calendar.DATE, 1);
			Calendar startday = (Calendar) event.getStartdate().clone();
			startday.add(Calendar.DATE, -1);

			logger.info("today: {}, startday: {}, endday: {}", today, startday, endday);
			logger.info("startday.after(today): {}, today.before(endday): {}", today.after(startday), today.before(endday));

			if(today.after(startday) && today.before(endday)) {
				logger.info("Update the event price when the date within the start and end date");
				for(Eventproduct ep : products) {
					Product product = ep.getProduct();
					BigDecimal eventprice = ep.getPrice(); //product.getRetail().subtract(product.getRetail().multiply(new BigDecimal(ep.getPercentage())).divide(new BigDecimal(100)));
					product.setEventprice(eventprice);
					product = productService.save(product);
				}
			} else {
				for(Eventproduct ep : products) {
					Product product = ep.getProduct();
					BigDecimal eventprice = null;
					product.setEventprice(eventprice);
					product = productService.save(product);
				}
			}

			model.addAttribute("products", products);
		}

		model.addAttribute("styles", styles);
		model.addAttribute("types", types);
		model.addAttribute("brands", brands);
		model.addAttribute("event", event);
		model.addAttribute("statuss", statuss);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Business");
		model.addAttribute("submenu", "giftcards");

		return rtn;
	}


	@PostMapping("/event/product")
	public String addEventproduct(@RequestParam("eventid") Long eventid,
			@RequestParam("productid") List<Long> productids,
			@RequestParam Map<String,String> requestParams,
			ModelMap model,
			HttpSession httpSession) {

		logger.info("[BSN] Add event product");

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String rtn = "redirect:/admin/event/" + eventid;

		logger.info("!!!!! dspEvent is called");
		/*Iterator it = requestParams.entrySet().iterator();
		while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry) it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue());
		}*/

		logger.info("Product IDs: {}", productids);
		Event event = eventService.getById(eventid);
		List<Eventproduct> eproducts = eventproductService.getByEventid(eventid);
		List<Long> eproductids = eproducts.stream().map(product -> product.getProduct().getProductid()).collect(Collectors.toList());

		Calendar today = Calendar.getInstance();
		Calendar endday = (Calendar) event.getEnddate().clone();
		endday.add(Calendar.DATE, 1);
		Calendar startday = (Calendar) event.getStartdate().clone();
		startday.add(Calendar.DATE, -1);

		for(Long productid: productids) {
			int index = eproductids.indexOf(productid);
			System.out.println("index: "+index);
			Product product = productService.getById(productid);
			String percentage = requestParams.get(productid.toString());
			String endprice = requestParams.get("price"+productid);
			Eventproduct ep = null;

			if(index < 0) {
				ep = new Eventproduct();
				ep.setEvent(event);
				ep.setProduct(product);

				if(percentage != null && !percentage.isEmpty()) {
					ep.setPercentage(Integer.parseInt(percentage));
				}

				BigDecimal eventprice = product.getRetail().subtract(product.getRetail().multiply(new BigDecimal(ep.getPercentage())).divide(new BigDecimal(100)));
				eventprice = eventprice.setScale(0,BigDecimal.ROUND_DOWN);

				if(endprice != null && !endprice.isEmpty()) {
					eventprice = eventprice.add(new BigDecimal(endprice));
				}

				eventprice.setScale(2);
				ep.setPrice(eventprice);

				ep = eventproductService.save(ep);

			} else {
				System.out.println("will update the event product "+eproductids.get(index));
				ep = eventproductService.getByEventidProductid(eventid, productid);

				if(percentage != null && !percentage.isEmpty()) {
					ep.setPercentage(Integer.parseInt(percentage));
				}

				BigDecimal eventprice = product.getRetail().subtract(product.getRetail().multiply(new BigDecimal(ep.getPercentage())).divide(new BigDecimal(100)));
				eventprice = eventprice.setScale(0,BigDecimal.ROUND_DOWN);
				System.out.println("event price: "+eventprice);
				if(endprice != null && !endprice.isEmpty()) {
					eventprice.setScale(2, BigDecimal.ROUND_UNNECESSARY);
					eventprice = eventprice.add(new BigDecimal(endprice));
					System.out.println("event price1: "+eventprice);
				}

				ep.setPrice(eventprice);

				ep = eventproductService.save(ep);
			}

			if(today.after(startday) && today.before(endday)) {
			//if(event.getStartdate().before(tomorrow) && event.getEnddate().after(yesterday)) {
				BigDecimal eventprice = ep.getPrice(); //product.getRetail().subtract(product.getRetail().multiply(new BigDecimal(ep.getPercentage())).divide(new BigDecimal(100)));
				product.setEventprice(eventprice);
				product = productService.save(product);
			}
		}

		for(Long productid: eproductids) {
			int index = productids.indexOf(productid);
			if(index < 0) {
				Eventproduct ep = eventproductService.getByEventidProductid(eventid, productid);
				Product p = ep.getProduct();
				p.setEventprice(BigDecimal.ZERO);
				p = productService.save(p);
				eventproductService.delete(ep);
			}
		}

		return rtn;
	}

}
