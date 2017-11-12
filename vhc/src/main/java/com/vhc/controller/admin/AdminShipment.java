package com.vhc.controller.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.vhc.model.Inventory;
import com.vhc.model.Item;
import com.vhc.model.Product;
import com.vhc.model.Region;
import com.vhc.model.Shipment;
import com.vhc.model.Size;
import com.vhc.model.Status;
import com.vhc.model.Store;
import com.vhc.model.Supplier;
import com.vhc.model.User;
import com.vhc.security.LoginUser;


/**
 * 
 * 
 * @author Jerry
 *
 */
@Controller
@RequestMapping({"/admin"})
public class AdminShipment extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(AdminShipment.class);
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/suppliers"})
	public String dspSuppliers(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/suppliers";
		
		List<Supplier> mfs = supplierService.getAll();
		model.addAttribute("suppliers", mfs);
		model.addAttribute("loginUser", getPrincipal());
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/supplier"})
	public String dspSupplier(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/supplier";
		
		List<City> cities = cityService.getAll();
		
		model.addAttribute("cities", cities);
		model.addAttribute("loginUser", getPrincipal());
		
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
		model.addAttribute("loginUser", getPrincipal());
		
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
		model.addAttribute("loginUser", getPrincipal());
		
		return "redirect: " + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/shipments"})
	public String dspShipments(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/shipments";
		
		List<Shipment> mfs = shipmentService.getAll();
		model.addAttribute("shipments", mfs);
		model.addAttribute("loginUser", getPrincipal());
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/shipment"})
	public String dspShipment(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/shipment";
		
		List<Supplier> suppliers = supplierService.getAll();
		
		model.addAttribute("suppliers", suppliers);
		model.addAttribute("loginUser", getPrincipal());
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/shipment/{shipmentid}"})
	public String updateShipment(ModelMap model, @PathVariable("shipmentid") Long shipmentid, HttpSession httpSession) {
		String rtn = "admin/shipment";
		
		long mfid = shipmentid.longValue();
		Shipment mf = shipmentService.getById(mfid);
		
		List<Supplier> suppliers = supplierService.getAll();
		List<Item> items = itemService.getByShipment(mfid);
		
		model.addAttribute("items", items);
		model.addAttribute("suppliers", suppliers);
		model.addAttribute("shipment", mf);
		model.addAttribute("loginUser", getPrincipal());
		
		return rtn;
	}

	
	@RequestMapping(method={RequestMethod.POST}, value={"/shipment"})
	public String doShipment(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) 
		throws Exception {
		
		String rtn = "shipments";
		User recordedby = this.getPrincipal();
		
		logger.info("doShipment is call!!!!! - " + recordedby.getUsername());
		
		String mfid = requestParams.get("shipmentid");
		String code = requestParams.get("code");
		String supplierid = requestParams.get("supplierid");
		long receivedby = Long.parseLong(requestParams.get("receivedby"));
		User recUser = userService.getById(receivedby);
		Calendar cal = Calendar.getInstance(); 
		String comments = requestParams.get("comments");
		String receivedate = requestParams.get("receivedate");  //
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		Calendar rcdCal = Calendar.getInstance();
		rcdCal.setTime(formatter.parse(receivedate));
		
		Supplier supplier = supplierService.getById(Long.parseLong(supplierid));
		
		Shipment mf = new Shipment();
		if(mfid != null && !mfid.isEmpty()) {
			mf.setShipmentid(Long.parseLong(mfid));
		}
		mf.setSupplier(supplier);
		mf.setCode(code);
		mf.setReceivedby(recUser);
		mf.setReceivedate(rcdCal);
		mf.setComments(comments);
		mf.setReceivedate(cal);
		mf.setRecordedby(recordedby);
		mf.setRecorddate(cal);
		try {
		shipmentService.save(mf);
		} catch(Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("loginUser", getPrincipal());

		return "redirect: " + rtn;
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/items"})
	public String dspItems(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/items";
		
		
		List<Item> items = itemService.getAll();
		model.addAttribute("items", items);
		model.addAttribute("loginUser", getPrincipal());
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/item"})
	public String dspItem(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "admin/item";
		String shipmentid = requestParams.get("shipmentid");
		List<Shipment> shipments = new ArrayList<>();
		
		if(shipmentid != null && !shipmentid.isEmpty()) {
			shipments.add(shipmentService.getById(Long.parseLong(shipmentid)));
		} else {
			shipments = shipmentService.getAll();
		}
		
		List<Size> sizes = sizeService.getAll();
		List<Region> regions = regionService.getAll();
		List<Product> products = productService.getAll();
		
		model.addAttribute("sizes", sizes);
		model.addAttribute("regions", regions);
		model.addAttribute("products", products);
		model.addAttribute("shipments", shipments);
		model.addAttribute("loginUser", getPrincipal());
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/item/{itemid}"})
	public String updateItem(ModelMap model, @PathVariable("itemid") Long itemid, HttpSession httpSession) {
		String rtn = "admin/item";
		
		long mfid = itemid.longValue();
		Item mf = itemService.getById(mfid);
		
		List<Shipment> shipments = shipmentService.getAll();
		List<Product> products = productService.getAll();
		List<Size> sizes = sizeService.getAll();
		List<Region> regions = regionService.getAll();
		List<Store> stores = storeService.getAll();
		List<Inventory> inventories = inventoryService.getByItem(mf);
		
		long sum = 0;
		for(Inventory i: inventories) { 
			sum += i.getQuantity();
		}
		
		model.addAttribute("sum", sum);
	
		model.addAttribute("sizes", sizes);
		model.addAttribute("regions", regions);
		model.addAttribute("shipments", shipments);
		model.addAttribute("products", products);
		model.addAttribute("item", mf);
		model.addAttribute("stores", stores);
		model.addAttribute("loginUser", getPrincipal());
		model.addAttribute("inventories", inventories);
		
		return rtn;
	}

	
	
	@RequestMapping(method={RequestMethod.POST}, value={"/item"})
	public String doItem(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) 
		throws Exception {
		
		String rtn = "items";
		User receivedby = this.getPrincipal();
		
		logger.info("doShipment is call!!!!! - " + receivedby.getUsername());
		
		String mfid = requestParams.get("itemid");
		String sku = requestParams.get("sku");
		String sizeid = requestParams.get("sizeid");
		String quantity = requestParams.get("quantity");
		String cost = requestParams.get("cost");
		String price = requestParams.get("price");
		String shipmentid = requestParams.get("shipmentid");
		String productid = requestParams.get("productid");
		String comments = requestParams.get("comments");
		
		String rtn_shipmentid = requestParams.get("rtn_shipmentid");
		//long receivedby = Long.parseLong(requestParams.get("receivedby"));
		System.out.println("++++++++++++++++rtn_shipmentid:" + rtn_shipmentid);
		Calendar cal = Calendar.getInstance(); 
		/*String receivedate = requestParams.get("receivedate");  //
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		Calendar cal = Calendar.getInstance();
		cal.setTime(formatter.parse(receivedate));*/
		
		Shipment shipment = shipmentService.getById(Long.parseLong(shipmentid));
		Product product = productService.getById(Long.parseLong(productid));
		Size size = sizeService.getById(Long.parseLong(sizeid));
		
		Item item = new Item();
		if(mfid != null && !mfid.isEmpty()) {
			item.setItemid(Long.parseLong(mfid));
		}
		item.setShipment(shipment);
		item.setSku(sku);
		item.setSize(size);
		item.setCost(Double.parseDouble(cost));
		item.setPrice(Double.parseDouble(price));
		item.setProduct(product);
		item.setReceivedby(receivedby);
		item.setComments(comments);
		item.setQuantity(Long.parseLong(quantity));
		item.setReceivedate(cal);
		try {
			itemService.save(item);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(rtn_shipmentid != null && !rtn_shipmentid.isEmpty()) {
			rtn = "shipment/" + rtn_shipmentid;
		}
		
		model.addAttribute("loginUser", getPrincipal());

		return "redirect: " + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/inventorys"})
	public String dspInventorys(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/inventorys";
		
		
		List<Inventory> inventorys = inventoryService.getAll();
		model.addAttribute("inventorys", inventorys);
		model.addAttribute("loginUser", getPrincipal());
		
		return rtn;
	}
	
	
	@RequestMapping(method={RequestMethod.GET}, value={"/inventory"})
	public String dspInventory(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "admin/inventory";
		String shipmentid = requestParams.get("shipmentid");
		List<Shipment> shipments = new ArrayList<>();
		
		if(shipmentid != null && !shipmentid.isEmpty()) {
			shipments.add(shipmentService.getById(Long.parseLong(shipmentid)));
		} else {
			shipments = shipmentService.getAll();
		}
		
		List<Item> items = itemService.getAll();
		List<Store> stores = storeService.getAll();
		List<Status> status = statusService.getAll();
		
		model.addAttribute("items", items);
		model.addAttribute("stores", stores);
		model.addAttribute("status", status);
		model.addAttribute("loginUser", getPrincipal());
		
		return rtn;
	}
	
	@RequestMapping(method={RequestMethod.POST}, value={"/inventory"})
	public String doInventory(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) 
		throws Exception {
		
		logger.info("!!!!! doInventory is called");
		String rtn = "redirect: item/" ;
		
		User receivedby = this.getPrincipal();
		
		String itemid = requestParams.get("itemid");
		String storeid = requestParams.get("storeid");
		String statusid = requestParams.get("statusid");
		String quantity = requestParams.get("quantity");
		Calendar cal = Calendar.getInstance(); 
		Item item = itemService.getById(Long.parseLong(itemid));
		Store store = storeService.getById(Long.parseLong(storeid));
		Status status = statusService.getById(Long.parseLong(statusid));
		
		Inventory inventory = new Inventory();
		inventory.setItem(item);
		inventory.setStore(store);
		inventory.setStatus(status);
		inventory.setQuantity(Long.parseLong(quantity));
		inventory.setReceivedby(receivedby);
		inventory.setReceivedate(cal);
		//try {
		inventory = inventoryService.save(inventory);
		/*} catch(Exception e) {
			e.
		}*/
		
		List<Inventory> inventories = inventoryService.getByItem(item);
		long sum = 0;
		for(Inventory i: inventories) { 
			sum += i.getQuantity();
		}
		
		model.addAttribute("sum", sum);
		model.addAttribute("inventories", inventories);
		
		rtn += itemid;
				
		return rtn;
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
