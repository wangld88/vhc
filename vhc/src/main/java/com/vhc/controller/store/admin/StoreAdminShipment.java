package com.vhc.controller.store.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.vhc.util.Message;
import com.vhc.controller.store.StoreBase;
import com.vhc.core.model.Address;
import com.vhc.core.model.City;
import com.vhc.core.model.Inventory;
import com.vhc.core.model.InventoryHistory;
import com.vhc.core.model.Item;
import com.vhc.core.model.Location;
import com.vhc.core.model.Product;
import com.vhc.core.model.Purchaseorder;
import com.vhc.core.model.Region;
import com.vhc.core.model.Shipment;
import com.vhc.core.model.Size;
import com.vhc.core.model.Staff;
import com.vhc.core.model.Status;
import com.vhc.core.model.Store;
import com.vhc.core.model.Supplier;
import com.vhc.core.model.User;
import com.vhc.security.LoginUser;


/**
 *
 *
 * @author K&J
 *
 */
@Controller
@RequestMapping({"/store/admin"})
public class StoreAdminShipment extends StoreBase {

	private final Logger logger = LoggerFactory.getLogger(StoreAdminShipment.class);


	@RequestMapping(method={RequestMethod.GET}, value={"/suppliers"})
	public String dspSuppliers(ModelMap model, HttpSession httpSession) {
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		String rtn = "store/admin/suppliers";

		List<Supplier> mfs = supplierService.getAll();

		model.addAttribute("store", store);
		model.addAttribute("suppliers", mfs);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "suppliers");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/supplier"})
	public String dspSupplier(ModelMap model, HttpSession httpSession) {
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		String rtn = "store/admin/supplier";

		List<City> cities = cityService.getAll();

		model.addAttribute("cities", cities);
		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "suppliers");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/supplier/{supplierid}"})
	public String updateSupplier(ModelMap model, @PathVariable("supplierid") Long supplierid, HttpSession httpSession) {
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		String rtn = "store/admin/supplier";

		long mfid = supplierid.longValue();
		Supplier mf = supplierService.getById(mfid);

		List<City> cities = cityService.getAll();

		model.addAttribute("mf", mf);
		model.addAttribute("cities", cities);
		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "suppliers");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/supplier"})
	public String doSupplier(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

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

		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "suppliers");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/purchorders"})
	public String dspPurchaseOrders(ModelMap model, HttpSession httpSession) {
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		String rtn = "store/admin/purchorders";

		List<Purchaseorder> orders = purchaseorderService.getAll();

		model.addAttribute("orders", orders);
		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "purchorders");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/purchorder"})
	public String dspPurchaseOrder(ModelMap model, HttpSession httpSession) {
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		String rtn = "store/admin/purchorder";

		List<Supplier> suppliers = supplierService.getAll();

		List<User> users = userService.getByRolename("ADMIN");

		model.addAttribute("users", users);
		model.addAttribute("suppliers", suppliers);
		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "purchorders");

		return rtn;
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/purchorder/{orderid}"})
	public String updatePurchaseOrder(ModelMap model, @PathVariable("orderid") Long orderid, HttpSession httpSession) {
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();
		String rtn = "store/admin/purchorder";

		long odrid = orderid.longValue();
		Purchaseorder order = purchaseorderService.getById(odrid);

		List<Supplier> suppliers = supplierService.getAll();
		List<Item> items = itemService.getByPurchaseorder(odrid);

		List<User> users = userService.getByRolename("ADMIN");

		List<Shipment> shipments = shipmentService.getByPO(order);

		model.addAttribute("notShipped", shipments.isEmpty());
		model.addAttribute("users", users);
		model.addAttribute("items", items);
		model.addAttribute("suppliers", suppliers);
		model.addAttribute("purchorder", order);
		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "purchorders");

		return rtn;
	}

	@RequestMapping(method={RequestMethod.POST}, value={"/purchorder"})
	public String doPurchaseOrder(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession)
		throws Exception {

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();
		String rtn = "purchorders";

		logger.info("doShipment is call!!!!! - " + loginUser.getUsername());

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

		Supplier supplier = supplierService.getById(Long.parseLong(supplierid));

		Purchaseorder order = new Purchaseorder();
		if(orderid != null && !orderid.isEmpty()) {
			order.setPoid(Long.parseLong(orderid));
		}
		order.setSupplier(supplier);
		order.setCode(code);
		if(expectdate != null && !expectdate.isEmpty()) {
			Calendar expCal = Calendar.getInstance();
			expCal.setTime(formatter.parse(expectdate));

			order.setExpectdate(expCal);
		}
		order.setSentby(sentUser);
		if(sentdate != null && !sentdate.isEmpty()) {
			Calendar rcdCal = Calendar.getInstance();
			rcdCal.setTime(formatter.parse(sentdate));
			order.setSentdate(rcdCal);
		}

		order.setComments(comments);
		order.setSentdate(cal);
		order.setRecordedby(loginUser);
		order.setRecorddate(cal);

		try {
			purchaseorderService.save(order);
		} catch(Exception e) {
			e.printStackTrace();
			if(orderid != null && !orderid.isEmpty()) {
				return "redirect:puchorder/"+orderid;
			} else {
				return "redirect:purchorder";
			}
		}
		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "purchorders");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/shipments"})
	public String dspShipments(ModelMap model, HttpSession httpSession) {
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		String rtn = "store/admin/shipments";

		List<Shipment> mfs = shipmentService.getAll();

		model.addAttribute("shipments", mfs);
		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "shipments");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/shipment"})
	public String dspShipment(ModelMap model, HttpSession httpSession) {
		String rtn = "store/admin/shipment";
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();


		List<Purchaseorder> orders = purchaseorderService.getAll();
		List<Supplier> suppliers = supplierService.getAll();
		List<Staff> staffs = staffService.getByStore(store);

		model.addAttribute("orders", orders);
		model.addAttribute("staffs", staffs);
		model.addAttribute("suppliers", suppliers);
		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "shipments");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/shipment/supplier/{supplierid}"})
	public String loadShipment(ModelMap model, @PathVariable("supplierid") Long supplierid, HttpSession httpSession) {
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		String rtn = "store/admin/shipment";

		long mfid = supplierid.longValue();
		Supplier supplier = supplierService.getById(mfid);
		//System.out.println("++++++++++++++++++");
		List<Purchaseorder> orders = purchaseorderService.getAvailablePOsBySupplier(supplier);
		//System.out.println("++++++++++++++++++");

		List<Supplier> suppliers = supplierService.getAll();
		//List<Item> items = itemService.getByShipment(mfid);

		//model.addAttribute("items", items);
		Shipment shipment = new Shipment();

		shipment.setSupplier(supplier);
		List<Staff> staffs = staffService.getByStore(store);

		model.addAttribute("staffs", staffs);
		model.addAttribute("orders", orders);
		model.addAttribute("suppliers", suppliers);
		model.addAttribute("shipment", shipment);
		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "shipments");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/shipment/{shipmentid}"})
	public String updateShipment(ModelMap model, @PathVariable("shipmentid") Long shipmentid, HttpSession httpSession) {
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		String rtn = "store/admin/shipment";

		long mfid = shipmentid.longValue();
		Shipment shipment = shipmentService.getById(mfid);
		List<Item> orderitems = new ArrayList<>();

		List<Supplier> suppliers = supplierService.getAll();
		List<Item> items = itemService.getByShipment(mfid);

		List<Purchaseorder> orders = purchaseorderService.getAvailablePOsBySupplier(shipment.getSupplier());

		if(shipment.getPurchaseorder() != null) {
			orders.add(shipment.getPurchaseorder());
			orderitems = shipment.getPurchaseorder().getItems();
		}

		if(items != null && !items.isEmpty() && !orderitems.isEmpty()) {
			orderitems = removeAdded(orderitems, items);
		}

		List<Staff> staffs = staffService.getByStore(store);

		List<Location> locations = locationService.getByStore(store);

		List<Inventory> inventories = inventoryService.getByShipment(shipment);

		model.addAttribute("orders", orders);
		model.addAttribute("staffs", staffs);
		model.addAttribute("inventories", inventories);
		model.addAttribute("orderitems", orderitems);
		model.addAttribute("suppliers", suppliers);
		model.addAttribute("shipment", shipment);
		model.addAttribute("locations", locations);
		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "shipments");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/shipment"})
	public String doShipment(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession)
		throws Exception {

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		String rtn = "shipments";

		logger.info("doShipment is call!!!!! - " + loginUser.getUsername());

		String mfid = requestParams.get("shipmentid");
		String code = requestParams.get("code");
		String supplierid = requestParams.get("supplierid");
		long receivedby = Long.parseLong(requestParams.get("receivedby"));
		String orderid = requestParams.get("orderid");
		User recUser = userService.getById(receivedby);
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		Calendar rcvCal = Calendar.getInstance();
		String receivedate = requestParams.get("receivedate");  //
		rcvCal.setTime(formatter.parse(receivedate));
		String comments = requestParams.get("comments");
		Calendar rcdCal = Calendar.getInstance();

		Supplier supplier = supplierService.getById(Long.parseLong(supplierid));

		Shipment mf = new Shipment();
		if(mfid != null && !mfid.isEmpty()) {
			mf.setShipmentid(Long.parseLong(mfid));
		}
		if(orderid != null && !orderid.isEmpty()) {
			Purchaseorder purchaseorder = purchaseorderService.getById(Long.parseLong(orderid));
			mf.setPurchaseorder(purchaseorder);
		}
		mf.setSupplier(supplier);
		mf.setCode(code);
		mf.setReceivedby(recUser);
		mf.setReceivedate(rcvCal);
		mf.setComments(comments);
		mf.setRecordedby(loginUser);
		mf.setRecorddate(rcdCal);

		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "shipments");

		try {
			shipmentService.save(mf);
		} catch(Exception e) {
			e.printStackTrace();
			if(mfid != null && !mfid.isEmpty()) {
				return "redirect:shipment/"+mfid;
			} else {
				return "redirect:shipment";
			}
		}

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/shipment/inventory"})
	public String addShipmentItem(@RequestParam Map<String,String> requestParams,
			ModelMap model,
			HttpSession httpSession) {
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		String rtn = "/store/admin/shipment";

		String shipmentid = requestParams.get("shipmentid");
		String itemid = requestParams.get("itemid");
		String sku = requestParams.get("sku");
		Shipment shipment = shipmentService.getById(Long.parseLong(shipmentid));
		Item item = itemService.getById(Long.parseLong(itemid));
		item.setShipment(shipment);
		item.setReceivedate(Calendar.getInstance());
		item.setReceivedby(loginUser);

		if(sku != null && !sku.isEmpty()) {
			item.setSku(sku);
		}

		item = itemService.save(item);

		Store store = staff.getStore();

		String storeid = requestParams.get("storeid");
		String statusid = requestParams.get("statusid");
		String quantity = requestParams.get("quantity");
		String locationid = requestParams.get("locationid");
		String uinventoryid = requestParams.get("uinventoryid");
		String inventoryid = requestParams.get("inventoryid");
		String tstoreid = requestParams.get("tstoreid");

		Inventory inventory = new Inventory();

		if(uinventoryid != null) {
			storeid = requestParams.get("ustoreid");
			quantity = requestParams.get("uquantity");
			locationid = requestParams.get("ulocationid");
			inventory = inventoryService.getById(Long.parseLong(uinventoryid));
		}

		if(inventoryid != null) {
			inventory = inventoryService.getById(Long.parseLong(inventoryid));
		}

		Calendar cal = Calendar.getInstance();
		Status status = statusService.getById(Long.parseLong(statusid));

		inventory.setItem(item);

		if(storeid == null || storeid.isEmpty()) {
			inventory.setStore(store);
		} else if(inventory.getStore() == null || inventory.getStore().getStoreid() != Long.parseLong(storeid)) {
			Store store1 = storeService.getById(Long.parseLong(storeid));
			inventory.setStore(store1);
		}

		if(tstoreid != null && !tstoreid.isEmpty()) {
			Store tstore = storeService.getById(Long.parseLong(tstoreid));
			inventory.setDeststore(tstore);
		}
		inventory.setStatus(status);
		if(inventory.getStatus().getStatusid() != status.getStatusid() && inventory.getStatus().getName().equals("Transferred")) {
			inventory.setDeststore(null);
			inventory.setLocation(null);
		}
		if(locationid != null && !locationid.isEmpty()) {
			Location location = locationService.getById(Long.parseLong(locationid));
			inventory.setLocation(location);
		}
		inventory.setQuantity(Long.parseLong(quantity));
		inventory.setReceivedby(loginUser);
		inventory.setReceivedate(cal);
		//try {
		inventory = inventoryService.save(inventory);
		/*} catch(Exception e) {
			e.
		}*/

		//List<Inventory> inventories = inventoryService.getByItem(item);
		List<Inventory> inventories = inventoryService.getByShipment(shipment);
		long sum = 0;
		for(Inventory i: inventories) {
			sum += i.getQuantity();
		}

		model.addAttribute("sum", sum);
		model.addAttribute("inventories", inventories);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("store", store);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "inventories");

		if(requestParams.get("shipmentid") != null) {
			return "redirect:/store/admin/shipment/" + requestParams.get("shipmentid");
		}

		return "redirect:" + rtn + "/" + shipmentid;
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/items"})
	public String dspItems(ModelMap model, HttpSession httpSession) {
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		String rtn = "store/admin/items";

		//List<Item> items = itemService.getAllAvailables();

		//model.addAttribute("items", items);
		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "items");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/item"})
	public String dspItem(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {

		logger.info("dspItem is called");
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();
		String rtn = "store/admin/item";
		String orderid = requestParams.get("orderid");
		String shipmentid = requestParams.get("shipmentid");
		List<Purchaseorder> orders = new ArrayList<>();
		List<Shipment> shipments = new ArrayList<>();
		List<Product> products = new ArrayList<>();

		//System.out.println("Loaded orderid: "+orderid);
		if(orderid != null && !orderid.isEmpty()) {
			orders.add(purchaseorderService.getById(Long.parseLong(orderid)));
		} else {
			orders = purchaseorderService.getAll();
		}

		if(shipmentid != null && !shipmentid.isEmpty()) {
			shipments.add(shipmentService.getById(Long.parseLong(shipmentid)));
		} else {
			shipments = shipmentService.getAll();
		}

		List<Size> sizes = sizeService.getAll();
		List<Region> regions = regionService.getAll();
		products = productService.getAll();
		//List<Purchaseorder> orders = purchaseorderService.getAll();
		List<Location> locations = locationService.getByStore(store);

		model.addAttribute("sizes", sizes);
		model.addAttribute("orders", orders);
		model.addAttribute("porderid", orderid);
		model.addAttribute("regions", regions);
		model.addAttribute("products", products);
		model.addAttribute("locations", locations);
		model.addAttribute("shipments", shipments);
		model.addAttribute("shipmentid", shipmentid);
		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "items");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/item/{itemid}"})
	public String updateItem(ModelMap model, @PathVariable("itemid") Long itemid, HttpSession httpSession) {
		String rtn = "store/admin/item";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		long mfid = itemid.longValue();
		Item item = itemService.getById(mfid);

		List<Shipment> shipments = shipmentService.getAll();
		List<Product> products = productService.getAll();
		List<Size> sizes = sizeService.getAll();
		List<Region> regions = regionService.getAll();

		List<Store> stores = new ArrayList<>();
		stores.add(store);
		List<Store> tstores = storeService.getAll();
		tstores.remove(store);
		List<Inventory> inventories = inventoryService.getByItem(item);
		List<Purchaseorder> orders = purchaseorderService.getAll();
		List<Location> locations = locationService.getByStore(store);

		long sum = 0;
		for(Inventory i: inventories) {
			sum += i.getQuantity();
		}

		model.addAttribute("sum", sum);

		model.addAttribute("sizes", sizes);
		model.addAttribute("orders", orders);
		model.addAttribute("regions", regions);
		model.addAttribute("shipments", shipments);
		model.addAttribute("products", products);
		model.addAttribute("item", item);
		model.addAttribute("stores", stores);
		model.addAttribute("tstores", tstores);
		model.addAttribute("locations", locations);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("inventories", inventories);
		model.addAttribute("store", store);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "items");

		return rtn;
	}



	@RequestMapping(method={RequestMethod.POST}, value={"/item"})
	public String doItem(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession)
	//public String doItem(@Valid ItemForm itemForm, BindingResult bindingResult, ModelMap model, HttpSession httpSession)
		throws Exception {
		logger.info("++++++Update Item++++++++++");
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();
		String rtn = "item";

		logger.info("doItem is call!!!!! - " + loginUser.getUsername());

		long qty = 0;
		long itmid = 0;
		String productname = requestParams.get("name");
		String itemid = requestParams.get("itemid");
		String sku = requestParams.get("sku");
		String sizeid = requestParams.get("sizeid");
		String quantity = requestParams.get("quantity");
		String cost = requestParams.get("cost");
		String orderid = requestParams.get("orderid");
		String ordercode = requestParams.get("ordercode");
		String shipmentid = requestParams.get("shipmentid");
		String shipmentcode = requestParams.get("shipmentcode");
		String comments = requestParams.get("comments");
		String locationid = requestParams.get("locationid");
		String addInventory = requestParams.get("addInventory");
		String returnurl = requestParams.get("return");

		String rtn_orderid = requestParams.get("rtn_orderid");
		String rtn_shipmentid = requestParams.get("rtn_shipmentid");
		Message msg = new Message();

		//System.out.println("orderid: "+orderid+", rtn_orderid: "+rtn_orderid);
		logger.info("sizeid is: "+sizeid);
		Calendar cal = Calendar.getInstance();

		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "items");

		if(productname == null || productname.isEmpty()) {
			msg.setStatus(Message.ERROR);
			msg.setMessage("Please provide product!");
			model.addAttribute("message", msg);
		} else if(sizeid == null || sizeid.isEmpty() || sizeid.equals("0")){
			msg.setStatus(Message.ERROR);
			msg.setMessage("Please provide size!");
			model.addAttribute("message", msg);
		} else {

			Product product = productService.getByFullname(productname);
			//Product product = productService.getById(Long.parseLong(productid));
			Size size = sizeService.getById(Long.parseLong(sizeid));

			if(product == null) {
				msg.setStatus(Message.ERROR);
				msg.setMessage("The provided product does not exist, please create the product first!");
				model.addAttribute("message", msg);
			} else if(size == null) {
				msg.setStatus(Message.ERROR);
				msg.setMessage("The provided size does not exist, please select a size!");
				model.addAttribute("message", msg);
			} else {
				if(itemid != null && !itemid.isEmpty()) {
					itmid = Long.parseLong(itemid);
				}
				//field validation
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
						item.setCreatedby(loginUser);
						item.setComments(comments);
						item.setCreationdate(cal);

						if(orderid != null && !orderid.isEmpty()) {
							Purchaseorder purchorder = purchaseorderService.getById(Long.parseLong(orderid));
							if(purchorder != null) {
								item.setPurchaseorder(purchorder);
							}
						} else if(ordercode != null && !ordercode.isEmpty()){
							Purchaseorder purchorder = purchaseorderService.getByCode(ordercode);
							if(purchorder != null) {
								item.setPurchaseorder(purchorder);
							}
						}

						if(shipmentid != null && !shipmentid.isEmpty()) {
							Shipment shipment = shipmentService.getById(Long.parseLong(shipmentid));
							if(shipment != null) {
								item.setShipment(shipment);
							}
						} else if(shipmentcode != null && !shipmentcode.isEmpty()) {
							Shipment shipment = shipmentService.getByCode(shipmentcode);
							if(shipment != null) {
								item.setShipment(shipment);
							}
						}

						if(cost != null && !cost.isEmpty()) {
							item.setCost(Double.parseDouble(cost));
						}

						item = itemService.save(item);
						itmid = item.getItemid();
						model.addAttribute("item", item);

						if(addInventory != null && !addInventory.isEmpty()) {
							Inventory inv = new Inventory();
							Status received = statusService.getByNameAndReftbl("Received", "inventories");

							inv.setStore(store);
							inv.setItem(item);
							inv.setReceivedate(cal);
							inv.setReceivedby(loginUser);
							inv.setStatus(received);
							inv.setQuantity(1);

							if(locationid != null && !locationid.isEmpty()) {
								Location location = locationService.getById(Long.parseLong(locationid));
								inv.setLocation(location);
							}

							inv = inventoryService.save(inv);
						}

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
					item.setCreatedby(loginUser);
					item.setComments(comments);
					item.setCreationdate(cal);

					if(orderid != null && !orderid.isEmpty()) {
						Purchaseorder purchorder = purchaseorderService.getById(Long.parseLong(orderid));
						if(purchorder != null) {
							item.setPurchaseorder(purchorder);
						}
					} else if(ordercode != null && !ordercode.isEmpty()){
						Purchaseorder purchorder = purchaseorderService.getByCode(ordercode);
						if(purchorder != null) {
							item.setPurchaseorder(purchorder);
						}
					}

					if(shipmentid != null && !shipmentid.isEmpty()) {
						Shipment shipment = shipmentService.getById(Long.parseLong(shipmentid));
						if(shipment != null) {
							item.setShipment(shipment);
						}
					} else if(shipmentcode != null && !shipmentcode.isEmpty()) {
						Shipment shipment = shipmentService.getByCode(shipmentcode);
						if(shipment != null) {
							item.setShipment(shipment);
						}
					}

					if(cost != null && !cost.isEmpty()) {
						item.setCost(Double.parseDouble(cost));
					}

					logger.info("item "+item.getItemid()+" comments: "+comments+", in DB: "+item.getComments());
					item = itemService.save(item);
					itmid = item.getItemid();
					model.addAttribute("item", item);

					if(addInventory != null && !addInventory.isEmpty()) {
						Inventory inv = new Inventory();
						Status status = statusService.getById(1);

						inv.setStore(store);
						inv.setItem(item);
						inv.setReceivedate(cal);
						inv.setReceivedby(loginUser);
						inv.setStatus(status);
						inv.setQuantity(1);

						if(locationid != null && !locationid.isEmpty()) {
							Location location = locationService.getById(Long.parseLong(locationid));
							inv.setLocation(location);
						}

						inv = inventoryService.save(inv);
					}
				}
				msg.setStatus(Message.SUCCESS);
				msg.setMessage("Item has been create/update successfully!");
			}

			if(rtn_orderid != null && !rtn_orderid.isEmpty()) {
				rtn = "purchorder/" + rtn_orderid;
				return "redirect:" + rtn;
			}

			if(rtn_shipmentid != null && !rtn_shipmentid.isEmpty()) {
				rtn = "shipment/" + rtn_shipmentid;
				return "redirect:" + rtn;
			}

			if(returnurl.equals("item")) {
				return "redirect:item";
			}
		}

		if(itmid == 0) {
			return "store/admin/item";
		} else {
			return "redirect:" + rtn + "/" + itmid;
		}
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/inventories"})
	public String dspInventories(ModelMap model, HttpSession httpSession) {
		String rtn = "store/admin/inventories";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		//move to datatable api call

//		Status received = statusService.getByNameAndReftbl("Received", "inventories");
//		Status returned = statusService.getByNameAndReftbl("Returned", "inventories");
//		Status transfered = statusService.getByNameAndReftbl("Transferred", "inventories");
//
//		List<Long> statuss = new ArrayList<>();
//		statuss.add(received.getStatusid());
//		statuss.add(returned.getStatusid());
//		statuss.add(transfered.getStatusid());

//		List<Inventory> inventories = inventoryService.getByStoreAvaiable(store.getStoreid(), statuss);
		////List<Inventory> inventories1 = inventoryService.getByStoreid(store.getStoreid());

		model.addAttribute("store", store);
//		model.addAttribute("inventories", inventories);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "inventories");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/inventory"})
	public String dspInventory(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		List<Store> stores = new ArrayList<>();
		stores.add(store);

		String rtn = "store/admin/item";

		String shipmentid = requestParams.get("shipmentid");
		List<Shipment> shipments = new ArrayList<>();

		if(shipmentid != null && !shipmentid.isEmpty()) {
			shipments.add(shipmentService.getById(Long.parseLong(shipmentid)));
		} else {
			shipments = shipmentService.getAll();
		}

		List<Item> items = itemService.getAll();
		List<Status> status = statusService.getAll();

		model.addAttribute("items", items);
		model.addAttribute("stores", stores);
		model.addAttribute("status", status);
		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "inventories");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/inventory/{inventoryid}"})
	public String updateInventory(@RequestParam Map<String,String> requestParams,
			ModelMap model,
			@PathVariable("inventoryid") Long inventoryid,
			HttpSession httpSession) {
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		String rtn = "store/admin/inventory"; //"redirect:item/";

		List<Store> stores = storeService.getAll();
		stores.remove(store);

		Inventory inv = inventoryService.getById(inventoryid);
		Item item = inv.getItem();

		List<Inventory> inventories = inventoryService.getByItem(item);
		long sum = 0;

		for(Inventory i: inventories) {
			sum += i.getQuantity();
		}

		List<Status> statuses = statusService.getByReftbl("inventories");
		List<Location> locations = locationService.getByStore(store);
		List<InventoryHistory> histories = inventoryHistoryService.getByInventory(inv);

		logger.info("[SAdm Inv] statuses.size: {}", statuses.size());

		model.addAttribute("store", store);
		model.addAttribute("stores", stores);
		model.addAttribute("locations", locations);
		model.addAttribute("statuses", statuses);
		model.addAttribute("sum", sum);
		model.addAttribute("inventories", inventories);
		model.addAttribute("inventory", inv);
		model.addAttribute("histories", histories);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "inventories");

		return rtn; //+ item.getItemid();
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/inventory"})
	public String doInventory(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession)
		throws Exception {

		logger.debug("!!!!! doInventory is called");
//System.out.println("!!!!! doInventory is called");
		String rtn = "redirect:/store/admin/inventories" ;

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Set<String> keys = requestParams.keySet();
		Iterator<String> itr = keys.iterator();

		while(itr.hasNext()) {
			String key = itr.next();
			System.out.println("Request Key: "+key+", Value: "+requestParams.get(key));
		}
		Store store = staff.getStore();

		String itemid = requestParams.get("itemid");
		String storeid = requestParams.get("storeid");
		String statusid = requestParams.get("statusid");
		String quantity = requestParams.get("quantity");
		String locationid = requestParams.get("locationid");
		String uinventoryid = requestParams.get("uinventoryid");
		String inventoryid = requestParams.get("inventoryid");
		String tstoreid = requestParams.get("tstoreid");

		Inventory inventory = new Inventory();

		//Update Inventory
		if(uinventoryid != null) {
			storeid = requestParams.get("ustoreid");
			quantity = requestParams.get("uquantity");
			locationid = requestParams.get("ulocationid");
			inventory = inventoryService.getById(Long.parseLong(uinventoryid));
		}

		if(inventoryid != null) {
			inventory = inventoryService.getById(Long.parseLong(inventoryid));
		}

		Calendar cal = Calendar.getInstance();
		Item item = itemService.getById(Long.parseLong(itemid));
		Status status = statusService.getById(Long.parseLong(statusid));
		InventoryHistory history = null;

		inventory.setItem(item);

		//Set to transfer
		if(tstoreid != null && !tstoreid.isEmpty()) {
			Store tstore = storeService.getById(Long.parseLong(tstoreid));

			System.out.println("@@@@@ Transfer to " + tstore.getName());

			inventory.setDeststore(tstore);
			inventory.setSenddate(cal);
			inventory.setSentby(loginUser);
		}

		if(storeid == null || storeid.isEmpty()) {
			inventory.setStore(store);
		} else if(inventory.getStore() == null || inventory.getStore().getStoreid() != Long.parseLong(storeid)) {
			Store store1 = storeService.getById(Long.parseLong(storeid));
			inventory.setStore(store1);
		}

		inventory.setQuantity(Long.parseLong(quantity));
		inventory.setReceivedby(loginUser);
		inventory.setReceivedate(cal);

		//System.out.println("Before Receive history status ID: "+inventory.getStatus().getStatusid());
		//Receive from Transfer
		if(inventory.getStatus() != null &&
				inventory.getStatus().getStatusid() != status.getStatusid() &&
				inventory.getStatus().getName().equals("Transferred")) {
			history = inventoryHistoryService.getUnreceivedTransfer(inventory, "Transferred");
			//System.out.println("INSIDE Receive history: "+history);

			if(history == null) {
				history = new InventoryHistory(inventory);
			} else {
				history.setReceivedby(loginUser);
				history.setReceivedate(cal);
			}
			history = inventoryHistoryService.save(history);

			inventory.setDeststore(null);
			inventory.setLocation(null);
			inventory.setStore(store);
		}

		inventory.setStatus(status);

		if(locationid != null && !locationid.isEmpty()) {
			Location location = locationService.getById(Long.parseLong(locationid));
			inventory.setLocation(location);
		}
		//try {
		inventory = inventoryService.save(inventory);

		//new Inventory
		if(inventoryid == null || status.getName().equals("Transferred")) {
			history = new InventoryHistory(inventory);
			history = inventoryHistoryService.save(history);
		}
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
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("store", store);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "inventories");

		if(requestParams.get("shipmentid") != null) {
			return "redirect:/store/admin/shipment/" + requestParams.get("shipmentid");
		}

		//rtn += itemid;

		return rtn;
	}

	@RequestMapping(method={RequestMethod.POST}, value={"/inventory/transfer"})
	public String doTransfer(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession)
		throws Exception {

		logger.debug("!!!!! doTransfer is called");
		String rtn = "redirect:/store/admin/item/" ;

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		String itemid = requestParams.get("itemid");
		String tstoreid = requestParams.get("tstoreid");
		//String statusid = requestParams.get("statusid");
		String uinventoryid = requestParams.get("tinventoryid");

		logger.debug("[Store ADM] Transfer - uinventoryid: {}", uinventoryid);
System.out.print("[Store ADM] Transfer - uinventoryid: "+uinventoryid);
		if(uinventoryid == null) {
			return rtn;
		}

		Inventory inventory = inventoryService.getById(Long.parseLong(uinventoryid));
		Calendar cal = Calendar.getInstance();
		Item item = itemService.getById(Long.parseLong(itemid));
		Store deststore = storeService.getById(Long.parseLong(tstoreid));
		Status transferred = statusService.getByNameAndReftbl("Transferred", "inventories");

		InventoryHistory history = new InventoryHistory(inventory);

		history = inventoryHistoryService.save(history);

		inventory.setStore(store);
		inventory.setStatus(transferred);
		inventory.setSentby(loginUser);
		inventory.setSenddate(cal);
		inventory.setReceivedate(null);
		inventory.setReceivedby(null);
		inventory.setDeststore(deststore);
		inventory.setLocation(null);

		//try {
		inventory = inventoryService.save(inventory);

		logger.debug("[Store ADM] Transfer - Date: {}, By: {}", inventory.getSenddate(), inventory.getSentby().getUserid());
		/*} catch(Exception e) {
			e.
		}*/
		System.out.print("[Store ADM] Transfer - Date: "+inventory.getSenddate()+", By: "+inventory.getSentby().getUserid());

		List<Inventory> inventories = inventoryService.getByItem(item);
		long sum = 0;
		for(Inventory i: inventories) {
			sum += i.getQuantity();
		}

		model.addAttribute("sum", sum);
		model.addAttribute("inventories", inventories);
		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "inventories");

		rtn += itemid;

		return rtn;
	}

	@RequestMapping(method={RequestMethod.POST}, value={"/inventory/transfer/cancel"})
	public String cancelTransfer(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession)
		throws Exception {

		logger.debug("!!!!! cancelTransfer is called");
		String rtn = "redirect:/store/admin/inventories" ;

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		String inventoryid = requestParams.get("inventoryid");

		Inventory inventory = inventoryService.getById(Long.parseLong(inventoryid));
		Calendar cal = Calendar.getInstance();
		//logger.debug("[Store ADM] Cancel Transfer - Date: {}, By: {}", inventory.getSenddate(), inventory.getSentby().getUserid());
		Status received = statusService.getByNameAndReftbl("Received", "inventories");
		Status canceled = statusService.getByNameAndReftbl("Cancelled", "payments");
		Item item = inventory.getItem();  //itemService.getById(Long.parseLong(itemid));

		inventory.setStatus(canceled);
		inventory.setDeststore(null);
		inventory.setReceivedate(cal);
		inventory.setReceivedby(loginUser);
		inventory.setComments("Transfer has been canceled by " + loginUser.getFirstname() + " " + loginUser.getLastname());

		inventory = inventoryService.save(inventory);

		InventoryHistory history1 = new InventoryHistory(inventory);
		history1 = inventoryHistoryService.save(history1);

		inventory.setStatus(received);
		inventory = inventoryService.save(inventory);
		InventoryHistory history = new InventoryHistory(inventory);
		history = inventoryHistoryService.save(history);

//		Store store = staff.getStore();
//
//		List<Inventory> inventories = inventoryService.getByItem(item);
//		long sum = 0;
//		for(Inventory i: inventories) {
//			sum += i.getQuantity();
//		}

		//model.addAttribute("sum", sum);
		//model.addAttribute("inventories", inventories);
		//model.addAttribute("store", store);
		//model.addAttribute("loginUser", loginUser);
		//model.addAttribute("menu", "Inventories");
		//model.addAttribute("subMenu", "inventories");

		//rtn += item.getItemid();

		return rtn;
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

	private List<Item> removeAdded(List<Item> target, List<Item> saleList) {

		List<Item> result = target.stream().filter(inv -> saleList.stream().allMatch(t -> inv.getItemid() != t.getItemid())).collect(Collectors.toList());

		return result;
	}
}
