package com.vhc.controller.admin;

import java.text.SimpleDateFormat;
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

import com.vhc.controller.BaseController;
import com.vhc.model.Address;
import com.vhc.model.City;
import com.vhc.model.Shipment;
import com.vhc.model.Supplier;


@Controller
@RequestMapping({"/admin"})
public class AdminShipment extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(AdminShipment.class);
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/suppliers"})
	public String dspSuppliers(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/suppliers";
		
		List<Supplier> mfs = supplierService.getAll();
		model.addAttribute("suppliers", mfs);
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/supplier"})
	public String dspSupplier(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/supplier";
		
		List<City> cities = cityService.getAll();
		
		model.addAttribute("cities", cities);
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/supplier/{supplierid}"})
	public String updateSupplier(ModelMap model, @PathVariable("supplierid") Long supplierid, HttpSession httpSession) {
		String rtn = "admin/supplier";
		
		long mfid = supplierid.longValue();
		Supplier mf = supplierService.getById(mfid);
		
		List<City> cities = cityService.getAll();
		model.addAttribute("mf", mf);
		model.addAttribute("cities", cities);
		
		return rtn;
	}

	
	@RequestMapping(method={RequestMethod.POST}, value={"/supplier"})
	public String doSupplier(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "suppliers";
		
		logger.info("doSupplier is call!!!!!");
		
		Address ads = new Address();
		
		String mfid = requestParams.get("supplierid");
		String street = (String) requestParams.get("street");
		String cityid = requestParams.get("cityid");
		String postalcode = (String) requestParams.get("postalcode");
		String name = (String) requestParams.get("name");
		String contact = (String) requestParams.get("contact");
		String phone = (String) requestParams.get("phone");
		String email = (String) requestParams.get("email");
		String website = (String) requestParams.get("website");
		String comments = (String) requestParams.get("comments");
		
		logger.info("cityid: " + cityid);
		City city = cityService.getById(Long.parseLong(cityid));
		ads.setCity(city);
		ads.setStreet(street);
		ads.setPostalcode(postalcode);
		
		addressService.save(ads);
		
		Supplier mf = new Supplier();
		if(mfid != null && !mfid.isEmpty()) {
			mf.setSupplierid(Long.parseLong(mfid));
		}
		mf.setAddress(ads);
		mf.setContact(contact);
		mf.setEmail(email);
		mf.setName(name);
		mf.setPhone(phone);
		mf.setWebsite(website);
		mf.setComments(comments);
		supplierService.save(mf);
		
		return "redirect: " + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/shipments"})
	public String dspShipments(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/shipments";
		
		List<Shipment> mfs = shipmentService.getAll();
		model.addAttribute("shipments", mfs);
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/shipment"})
	public String dspShipment(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/shipment";
		
		List<City> cities = cityService.getAll();
		
		model.addAttribute("cities", cities);
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/shipment/{shipmentid}"})
	public String updateShipment(ModelMap model, @PathVariable("shipmentid") Long shipmentid, HttpSession httpSession) {
		String rtn = "admin/shipment";
		
		long mfid = shipmentid.longValue();
		Shipment mf = shipmentService.getById(mfid);
		
		List<City> cities = cityService.getAll();
		model.addAttribute("mf", mf);
		model.addAttribute("cities", cities);
		
		return rtn;
	}

	
	@RequestMapping(method={RequestMethod.POST}, value={"/shipment"})
	public String doShipment(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) 
		throws Exception {
		
		String rtn = "shipments";
		
		logger.info("doShipment is call!!!!!");
		
		Address ads = new Address();
		
		String mfid = requestParams.get("shipmentid");
		String code = requestParams.get("code");
		String supplierid = requestParams.get("supplierid");
		String receivedby = requestParams.get("receivedby");
		String receivedate = requestParams.get("receivedate");
		String comments = requestParams.get("comments");
		
		Supplier supplier = supplierService.getById(Long.parseLong(supplierid));
		
		Shipment mf = new Shipment();
		if(mfid != null && !mfid.isEmpty()) {
			mf.setShipmentid(Long.parseLong(mfid));
		}
		mf.setSupplier(supplier);
		mf.setCode(code);
		mf.setReceivedby(receivedby);
		mf.setComments(comments);
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		Calendar cal = Calendar.getInstance();
		cal.setTime(formatter.parse(receivedate));
		mf.setReceivedate(cal);
		
		shipmentService.save(mf);
		
		return "redirect: " + rtn;
	}
}
