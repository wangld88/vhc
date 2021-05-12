package com.vhc.controller.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vhc.core.model.Address;
import com.vhc.core.model.City;
import com.vhc.core.model.Countinventory;
import com.vhc.core.model.Countlog;
import com.vhc.core.model.Countupload;
import com.vhc.core.model.Inventory;
import com.vhc.core.model.Inventorycount;
import com.vhc.core.model.Item;
import com.vhc.core.model.Product;
import com.vhc.core.model.Purchaseorder;
import com.vhc.core.model.Region;
import com.vhc.core.model.Shipment;
import com.vhc.core.model.Size;
import com.vhc.core.model.Status;
import com.vhc.core.model.Store;
import com.vhc.core.model.Supplier;
import com.vhc.core.model.User;
import com.vhc.core.util.Message;
import com.vhc.service.util.ExcelProcessor;


/**
 *
 *
 * @author K & J Consulting
 *
 */
@Controller
@RequestMapping({"/admin"})
public class AdminShipment extends AdminBase {

	private final Logger logger = LoggerFactory.getLogger(AdminShipment.class);


	@RequestMapping(method={RequestMethod.GET}, value={"/suppliers"})
	public String dspSuppliers(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/suppliers";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		List<Supplier> mfs = supplierService.getAll();
		model.addAttribute("suppliers", mfs);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "suppliers");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/suppliers/search"})
	public String searchSupplier(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "admin/suppliers";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String name = "%" + requestParams.get("name") + "%";
		List<Supplier> suppliers = supplierService.getByName(name);

		model.addAttribute("suppliers", suppliers);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "suppliers");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/supplier"})
	public String dspSupplier(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/supplier";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		List<City> cities = cityService.getAll();

		model.addAttribute("cities", cities);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "suppliers");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/supplier/{supplierid}"})
	public String updateSupplier(ModelMap model, @PathVariable("supplierid") Long supplierid, HttpSession httpSession) {
		String rtn = "admin/supplier";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		long mfid = supplierid.longValue();
		Supplier mf = supplierService.getById(mfid);

		List<City> cities = cityService.getAll();
		model.addAttribute("mf", mf);
		model.addAttribute("cities", cities);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "suppliers");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/supplier"})
	public String doSupplier(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "suppliers";

		logger.info("doSupplier is call!!!!!");
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

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
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Products");
		model.addAttribute("submenu", "suppliers");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/purchorders"})
	public String dspPurchaseOrders(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/purchorders";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		List<Purchaseorder> orders = purchaseorderService.getAll();
		model.addAttribute("orders", orders);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "purchorders");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/purchorders/search"})
	public String searchPurchaseOrders(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "admin/purchorders";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String name = "%" + requestParams.get("name") + "%";
		List<Purchaseorder> purchorders = purchaseorderService.getByName(name);

		model.addAttribute("orders", purchorders);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "purchorders");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/purchorder"})
	public String dspPurchaseOrder(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/purchorder";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		List<Supplier> suppliers = supplierService.getAll();

		List<User> users = userService.getByRolename("ADMIN");

		model.addAttribute("users", users);
		model.addAttribute("suppliers", suppliers);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "purchorders");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/purchorder/{orderid}"})
	public String updatePurchaseOrder(ModelMap model, @PathVariable("orderid") Long orderid, HttpSession httpSession) {
		String rtn = "admin/purchorder";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		long odrid = orderid.longValue();
		Purchaseorder order = purchaseorderService.getById(odrid);

		List<Supplier> suppliers = supplierService.getAll();
		List<Item> items = itemService.getByPurchaseorder(odrid);

		List<User> users = userService.getByRolename("ADMIN");

		model.addAttribute("users", users);
		model.addAttribute("items", items);
		model.addAttribute("suppliers", suppliers);
		model.addAttribute("purchorder", order);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "purchorders");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/purchorder"})
	public String doPurchaseOrder(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession)
		throws Exception {

		String rtn = "purchorders";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		User recordedby = loginUser;

		logger.info("doShipment is call!!!!! - " + recordedby.getUsername());

		String orderid = requestParams.get("orderid");
		String code = requestParams.get("code");
		String supplierid = requestParams.get("supplierid");
		long sentby = Long.parseLong(requestParams.get("sentby"));
		User sentUser = userService.getById(sentby);
		Calendar cal = Calendar.getInstance();
		String comments = requestParams.get("comments");
		String sentdate = requestParams.get("sentdate");
		String expectdate = requestParams.get("expectdate");
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		Calendar rcdCal = Calendar.getInstance();
		rcdCal.setTime(formatter.parse(sentdate));
		Calendar expCal = Calendar.getInstance();
		expCal.setTime(formatter.parse(expectdate));

		Supplier supplier = supplierService.getById(Long.parseLong(supplierid));

		Purchaseorder order = new Purchaseorder();
		if(orderid != null && !orderid.isEmpty()) {
			order.setPoid(Long.parseLong(orderid));
		}
		order.setSupplier(supplier);
		order.setCode(code);
		order.setExpectdate(expCal);
		order.setSentby(sentUser);
		order.setSentdate(rcdCal);

		if(comments != null && !comments.isEmpty()) {
			order.setComments(comments);
		}

		order.setSentdate(cal);
		order.setRecordedby(recordedby);
		order.setRecorddate(cal);

		purchaseorderService.save(order);

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "purchorders");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/shipments"})
	public String dspShipments(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/shipments";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}
		List<Shipment> mfs = shipmentService.getAll();
		model.addAttribute("shipments", mfs);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "shipments");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/shipments/search"})
	public String searchShipments(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "admin/shipments";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String name = "%" + requestParams.get("name") + "%";
		List<Shipment> shipments = shipmentService.getByName(name);

		model.addAttribute("shipments", shipments);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "shipments");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/shipment"})
	public String dspShipment(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/shipment";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		List<Supplier> suppliers = supplierService.getAll();

		model.addAttribute("suppliers", suppliers);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "shipments");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/shipment/{shipmentid}"})
	public String updateShipment(ModelMap model, @PathVariable("shipmentid") Long shipmentid, HttpSession httpSession) {
		String rtn = "admin/shipment";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		long mfid = shipmentid.longValue();
		Shipment mf = shipmentService.getById(mfid);

		List<Supplier> suppliers = supplierService.getAll();
		List<Item> items = itemService.getByShipment(mfid);

		model.addAttribute("items", items);
		model.addAttribute("suppliers", suppliers);
		model.addAttribute("shipment", mf);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "shipments");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/shipment"})
	public String doShipment(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession)
		throws Exception {

		String rtn = "shipments";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		User recordedby = loginUser;

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
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "shipments");

		return "redirect:" + rtn;
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/items"})
	public String dspItems(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/items";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}
		//list all
		//List<Item> allItems = itemService.getAll();

		List<Item> items = itemService.getAllAvailables();

		//System.out.println("All Items: "+allItems.size()+", available items: "+items.size());

		model.addAttribute("items", items);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "items");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/items/search"})
	public String searchItem(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "admin/items";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String name = "%" + requestParams.get("name") + "%";
		List<Item> items = itemService.getByName(name);

		model.addAttribute("items", items);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "items");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/item"})
	public String dspItem(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		logger.info("dspItem: ");
		String rtn = "admin/item";
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

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
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "items");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/item/{itemid}"})
	public String updateItem(ModelMap model, @PathVariable("itemid") Long itemid, HttpSession httpSession) {
		String rtn = "admin/item";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

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
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("inventories", inventories);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "items");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/items/{itemid}"})
	public String removeItem(ModelMap model, @PathVariable("itemid") Long itemid, HttpSession httpSession) {
		String rtn = "/admin/items";
		System.out.println("removeProduct is call!!!!!");
		logger.info("removeProduct is call!!!!! "+itemid);
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}
		long itmid = itemid.longValue();

		try {
			Inventory inventory = inventoryService.getByItemid(itmid);

			if(inventory != null) {
				inventoryService.delete(inventory.getInventoryid());
			}

			itemService.delete(itmid);
		} catch(Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "items");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/item"})
	public String doItem(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession)
		throws Exception {

		String rtn = "item";
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		logger.info("doShipment is call!!!!! - " + loginUser.getUsername());

		String productname = requestParams.get("prodname");
		String itemid = requestParams.get("itemid");
		String sku = requestParams.get("sku");
		String sizeid = requestParams.get("sizeid");
		String quantity = requestParams.get("quantity");
		String cost = requestParams.get("cost");
		//String price = requestParams.get("price");
		String orderid = requestParams.get("orderid");
		String shipmentid = requestParams.get("shipmentid");
		String productid = requestParams.get("productid");
		String comments = requestParams.get("comments");

		String rtn_orderid = requestParams.get("rtn_orderid");
		String rtn_shipmentid = requestParams.get("rtn_shipmentid");
		//long receivedby = Long.parseLong(requestParams.get("receivedby"));

		Calendar cal = Calendar.getInstance();
		/*String receivedate = requestParams.get("receivedate");  //
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		Calendar cal = Calendar.getInstance();
		cal.setTime(formatter.parse(receivedate));*/

		long qty = 0;
		long itmid = 0;
		//Product product = productService.getById(Long.parseLong(productid));
		Product product = productService.getByFullname(productname);
		Size size = sizeService.getById(Long.parseLong(sizeid));

		if(product == null && productid != null) {
			product = productService.getById(Long.parseLong(productid));
		}

		if(itemid != null && !itemid.isEmpty()) {
			itmid = Long.parseLong(itemid);
		}
		if(quantity != null && !quantity.isEmpty()) {
			qty = Long.parseLong(quantity);
		}

		if(itmid == 0 && qty > 1) {
			for(long i = 0; i < qty; i++) {
				Item item = new Item();
				item.setQuantity(1);
				item.setSku(sku);
				item.setSize(size);
				//item.setPrice(Double.parseDouble(price));
				item.setProduct(product);
				item.setReceivedby(loginUser);
				item.setComments(comments);
				item.setReceivedate(cal);

				if(orderid != null && !orderid.isEmpty()) {
					Purchaseorder purchorder = purchaseorderService.getById(Long.parseLong(orderid));
					item.setPurchaseorder(purchorder);
				}
				if(shipmentid != null && !shipmentid.isEmpty()) {
					Shipment shipment = shipmentService.getById(Long.parseLong(shipmentid));
					item.setShipment(shipment);
				}

				if(cost != null && !cost.isEmpty()) {
					item.setCost(Double.parseDouble(cost));
				}

				itemService.save(item);
				itmid = item.getItemid();
			}

		} else {
			Item item = new Item();
			if(itmid != 0) {
				item.setItemid(itmid);
			}
			item.setQuantity(1);
			item.setSku(sku);
			item.setSize(size);
			//item.setPrice(Double.parseDouble(price));
			item.setProduct(product);
			item.setReceivedby(loginUser);
			item.setComments(comments);
			item.setReceivedate(cal);

			if(orderid != null && !orderid.isEmpty()) {
				Purchaseorder purchorder = purchaseorderService.getById(Long.parseLong(orderid));
				item.setPurchaseorder(purchorder);
			}
			if(shipmentid != null && !shipmentid.isEmpty()) {
				Shipment shipment = shipmentService.getById(Long.parseLong(shipmentid));
				item.setShipment(shipment);
			}

			if(cost != null && !cost.isEmpty()) {
				item.setCost(Double.parseDouble(cost));
			}

			itemService.save(item);
			itmid = item.getItemid();
		}

		/*Item item = new Item();
		if(mfid != null && !mfid.isEmpty()) {
			item.setItemid(Long.parseLong(mfid));
		}
		Shipment shipment = shipmentService.getById(Long.parseLong(shipmentid));
		item.setShipment(shipment);
		item.setSku(sku);
		item.setSize(size);
		if(cost != null && !cost.isEmpty()) {
			item.setCost(Double.parseDouble(cost));
		}

		//item.setPrice(Double.parseDouble(price));
		item.setProduct(product);
		item.setReceivedby(receivedby);
		item.setComments(comments);
		if(quantity != null && !quantity.isEmpty()) {
			item.setQuantity(Long.parseLong(quantity));
		}

		item.setReceivedate(cal);
		try {
			itemService.save(item);
		} catch(Exception e) {
			e.printStackTrace();
		}*/

		if(rtn_orderid != null && !rtn_orderid.isEmpty()) {
			rtn = "purchorder/" + rtn_orderid;
		}

		if(rtn_shipmentid != null && !rtn_shipmentid.isEmpty()) {
			rtn = "shipment/" + rtn_shipmentid;
		}

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "items");

		return "redirect:" + rtn + "/" + itmid;
	}


	@GetMapping("/inventorycounts")
	public String dspInventoryCounts(ModelMap model, HttpSession httpSession) {

		String rtn = "admin/inventorycounts";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		logger.info("[ADM Ship] dspInventoryCounts is call!!!!! ");

		List<Inventorycount> inventorycounts = inventorycountService.getAll();
		//List<Countlog> countlogs = countlogService.getAll();

		model.addAttribute("counts", inventorycounts);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "inventorycounts");

		return rtn;

	}

	@GetMapping("/inventorycount/")
	public String dspNewInventoryCount(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/inventorycount";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		//model.addAttribute("logs", logs);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "inventorycounts");

		return rtn;
	}

	@PostMapping("/inventorycount/")
	public String doNewInventoryCount(ModelMap model, HttpSession httpSession) {
		String rtn = "redirect:/admin/inventorycount";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		Status status = statusService.getByNameAndReftbl("Started", "inventorycounts");
		List<Status> statuss = statusService.getByReftblExclude("Delivered", "inventories");
		List<Inventory> invs = inventoryService.getAllAvaiable(statuss);

		Calendar cal = Calendar.getInstance();
		Inventorycount count = new Inventorycount();
		count.setCreatedby(loginUser);
		count.setCreationdate(cal);
		count.setCounted(0);
		count.setScanned(0);
		count.setTotal(invs.size());
		count.setStatus(status);

		count = inventorycountService.save(count);

		for(Inventory inv : invs) {
			Countinventory cinv = new Countinventory();
			cinv.setInventory(inv);
			Item itm = inv.getItem();
			Product p = itm.getProduct();
			cinv.setProduct(p.getName());
			cinv.setInventory(inv);
			cinv.setModelnum(p.getModelnum());
			cinv.setSku(itm.getSku());
			if(itm.getSize().getRegion() != null) {
				cinv.setSizenum(itm.getSize().getRegion().getCode() + itm.getSize().getSizenum());
			}
			cinv.setBrand(p.getBrand().getName());
			cinv.setCount(count);
			if(inv.getLocation() != null) {
				cinv.setLocation(inv.getLocation().getName());
			} else {
				cinv.setLocation("N/A");
			}
			cinv.setStore(inv.getStore().getName());

			cinv = countinventoryService.save(cinv);
		}

		//model.addAttribute("logs", logs);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "inventorycounts");

		return rtn+count.getCountid();
	}

	@GetMapping("/inventorycount/{countid}")
	public String dspInventoryCount(ModelMap model, @PathVariable("countid") Long countid, HttpSession httpSession) {
		String rtn = "admin/inventorycount";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		Inventorycount count = inventorycountService.getById(countid);
		List<Countupload> uploads = countuploadService.getByCount(count);
		//List<Countlog> logs = countlogService.getByUpload(upload);

		model.addAttribute("uploads", uploads);
		model.addAttribute("count", count);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "inventorycounts");

		return rtn;
	}

	@PostMapping("/inventorycount/complete/{countid}")
	public String completeInventoryCount(ModelMap model,
			@PathVariable("countid") Long countid,
			HttpSession httpSession) {
		String rtn = "admin/inventorycount";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		Calendar cal = Calendar.getInstance();
		Status status = statusService.getByNameAndReftbl("Completed", "inventorycounts");
		Inventorycount count = inventorycountService.getById(countid);

		count.setStatus(status);
		count.setUpdatedate(cal);
		count.setUpdatedby(loginUser);
		count = inventorycountService.save(count);

		List<Countupload> uploads = countuploadService.getByCount(count);

		model.addAttribute("uploads", uploads);
		model.addAttribute("count", count);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "inventorycounts");

		return rtn;
	}

	@GetMapping("/inventorycount/countlog/{countuploadid}")
	public String dspCountlog(ModelMap model,
			@PathVariable("countuploadid") Long countuploadid,
			HttpSession httpSession) {

		String rtn = "admin/countlog";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		Countupload upload = countuploadService.getById(countuploadid);
		List<Countlog> logs = countlogService.getByUpload(upload);

		model.addAttribute("logs", logs);
		model.addAttribute("count", upload.getCount());
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "inventorycounts");

		return rtn;
	}

	@GetMapping("/inventorycount/shortage/{countid}")
	public String dspCountShortage(ModelMap model,
			@PathVariable("countid") Long countid,
			HttpSession httpSession) {
		String rtn = "admin/countshortage";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		Inventorycount count = inventorycountService.getById(countid);

		List<Countinventory> invs = countinventoryService.getShortageByCount(count);

		model.addAttribute("cinventories", invs);
		model.addAttribute("count", count);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "inventorycounts");

		return rtn;
	}


	@GetMapping("/inventorycount/overage/{countid}")
	public String dspCountOverage(ModelMap model,
			@PathVariable("countid") Long countid,
			HttpSession httpSession) {
		String rtn = "admin/countoverage";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		Inventorycount count = inventorycountService.getById(countid);

		List<Countlog> logs = countlogService.getOverageByCount(count);

		model.addAttribute("logs", logs);
		model.addAttribute("count", count);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "inventorycounts");

		return rtn;
	}


	@GetMapping("/inventoryupload")
	public String dspInventoryUpload(ModelMap model, @RequestParam Map<String,String> requestParams, HttpSession httpSession) {
		String rtn = "admin/inventoryupload";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		String countid = requestParams.get("countid");

		Inventorycount count = inventorycountService.getById(Long.parseLong(countid));
		//model.addAttribute("statuss", statuss);
		model.addAttribute("count", count);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "inventorycounts");

		return rtn;
	}

	@RequestMapping(method={RequestMethod.POST}, value={"/inventoryupload"})
	public String doInventoryUpload(@RequestParam("file") MultipartFile file,
			@RequestParam Map<String,String> requestParams,
			ModelMap model, HttpSession httpSession) {
		String rtn = "/admin/inventorycount/countlog/";
		User loginUser = getSuperAdmin();
		Message msg = new Message();

		logger.info("Inventory count upload is called!!!");
		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		try {
		    InputStream in = file.getInputStream();
		    logger.info("$$$$$$$$$$$$$$$$$$ environment:{}, filePath: {}", environment, filePath);
		    File currDir = new File(filePath); //new File("C:\\temp\\"); //"/disk2/upload"
		    String path = currDir.getAbsolutePath();
		    String fileLocation = path + "/" + file.getOriginalFilename(); //path.substring(0, path.length() - 1) + file.getOriginalFilename();
		    logger.info("File Location: "+fileLocation+", path: "+path);

		    FileOutputStream f = new FileOutputStream(fileLocation);
		    int ch = 0;
		    while ((ch = in.read()) != -1) {
		        f.write(ch);
		    }
		    f.flush();
		    f.close();

		    //List<Store> stores = storeService.getAll();

		    ExcelProcessor processor = new ExcelProcessor();

			List<String> skus = processor.readInventory(fileLocation);
			List<Countlog> logs = new ArrayList<>();

			List<Status> status = statusService.getByReftblExclude("Delivered", "inventories");
			logger.warn("Size of the upload cards: {}", logs.size());

			long total = 0;
			Calendar cal = Calendar.getInstance();

			String countid = requestParams.get("countid");

			Inventorycount count = inventorycountService.getById(Long.parseLong(countid));

			if(!skus.isEmpty()) {
				Countupload upload = new Countupload();
				upload.setCount(count);
				upload.setFilename(file.getOriginalFilename());
				upload.setCreationdate(cal);
				upload.setCreatedby(loginUser);
				upload = countuploadService.save(upload);

				List<Countinventory> countInvs = new ArrayList<>();

				for(String sku : skus) {

					sku = sku.trim();
					List<Countinventory> invs = countinventoryService.getAvailableByUPC(sku);
					Countlog newlog = new Countlog();
					newlog.setCreatedby(loginUser);
					newlog.setCreationdate(cal);
					newlog.setSku(sku);
					newlog.setUpload(upload);

					newlog = countlogService.save(newlog);
					logs.add(newlog);

					if(!invs.isEmpty()) {
						Countinventory inv = invs.get(0);
						newlog.setInventory(inv.getInventory());
						inv.setCountlog(newlog);
						inv = countinventoryService.save(inv);
						newlog = countlogService.save(newlog);

						countInvs.add(inv);
					}

					//newlog.setCount(count);
					total++;
				}

				upload.setTotal(total);
				upload.setCounted(countInvs.size());
				upload = countuploadService.save(upload);

				rtn += upload.getCountuploadid();

				//update Inventory Count
				count.setCounted(count.getCounted() + countInvs.size());
				count.setScanned(count.getScanned() + total);
				count = inventorycountService.save(count);

				msg.setStatus(Message.SUCCESS);
				msg.setMessage("The inventory count batch process completed successfully!");
			}

		} catch(Exception e) {
			msg.setStatus(Message.ERROR);
			msg.setMessage("The inventory count batch process failed, found error: " + e.getMessage());
			e.printStackTrace();
			logger.error("The inventory count batch process failed, found error: " + e.getMessage());
		}

		List<City> cities = cityService.getAll();
		List<Status> statuss = statusService.getByReftbl("inventories");

		model.addAttribute("message", msg);
		model.addAttribute("statuss", statuss);
		model.addAttribute("cities", cities);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "inventorycounts");

		return "redirect:"+rtn;
	}


	@GetMapping("/countinventory/{countid}")
	public String dspCountinventory(ModelMap model,
			@PathVariable("countid") Long countid,
			HttpSession httpSession) {

		String rtn = "admin/countinventory";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			return "redirect:/admin/logout";
		}

		Inventorycount count = inventorycountService.getById(countid);

		List<Countinventory> invs = countinventoryService.getByCount(count);

		model.addAttribute("cinventories", invs);
		model.addAttribute("count", count);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "inventorycounts");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/inventorys"})
	public String dspInventorys(ModelMap model, HttpSession httpSession) {
		String rtn = "admin/inventorys";

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		List<Inventory> inventorys = inventoryService.getAll();
		model.addAttribute("inventorys", inventorys);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "inventorys");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/inventory"})
	public String dspInventory(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "admin/inventory";
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}
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
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "inventorys");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/inventory/{inventoryid}"})
	public String updateInventory(ModelMap model, @PathVariable("inventoryid") Long inventoryid, HttpSession httpSession) {
		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}

		String rtn = "admin/inventory";

		Inventory inventory = inventoryService.getById(inventoryid);

		List<Item> items = itemService.getAll();
		List<Store> stores = storeService.getAll();
		List<Status> status = statusService.getAll();

		model.addAttribute("items", items);
		model.addAttribute("stores", stores);
		model.addAttribute("status", status);
		model.addAttribute("inventory", inventory);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "inventorys");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/inventory"})
	public String doInventory(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession)
		throws Exception {

		logger.info("!!!!! doInventory is called");
		String rtn = "redirect:item/" ;

		User loginUser = getSuperAdmin();

		if(loginUser == null) {
			logger.error("The login user is not a super admin.");
			return "redirect:/admin/logout";
		}
		User receivedby = loginUser;

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
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("inventories", inventories);
		model.addAttribute("adminmenu", "Inventories");
		model.addAttribute("submenu", "inventorys");

		rtn += itemid;

		return rtn;
	}

}
