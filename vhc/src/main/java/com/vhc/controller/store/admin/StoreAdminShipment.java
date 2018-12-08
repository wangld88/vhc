package com.vhc.controller.store.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vhc.controller.BaseController;
import com.vhc.util.Message;
import com.vhc.dto.ItemForm;
import com.vhc.model.Address;
import com.vhc.model.City;
import com.vhc.model.Inventory;
import com.vhc.model.Item;
import com.vhc.model.Location;
import com.vhc.model.Product;
import com.vhc.model.Purchaseorder;
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
@RequestMapping({"/store/admin"})
public class StoreAdminShipment extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(StoreAdminShipment.class);


	@RequestMapping(method={RequestMethod.GET}, value={"/suppliers"})
	public String dspSuppliers(ModelMap model, HttpSession httpSession) {
		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		String rtn = "store/admin/suppliers";

		List<Supplier> mfs = supplierService.getAll();
		model.addAttribute("suppliers", mfs);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "suppliers");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/supplier"})
	public String dspSupplier(ModelMap model, HttpSession httpSession) {
		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		String rtn = "store/admin/supplier";

		List<City> cities = cityService.getAll();

		model.addAttribute("cities", cities);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "suppliers");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/supplier/{supplierid}"})
	public String updateSupplier(ModelMap model, @PathVariable("supplierid") Long supplierid, HttpSession httpSession) {
		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		String rtn = "store/admin/supplier";

		long mfid = supplierid.longValue();
		Supplier mf = supplierService.getById(mfid);

		List<City> cities = cityService.getAll();
		model.addAttribute("mf", mf);
		model.addAttribute("cities", cities);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "suppliers");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/supplier"})
	public String doSupplier(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
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
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "suppliers");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/purchorders"})
	public String dspPurchaseOrders(ModelMap model, HttpSession httpSession) {
		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		String rtn = "store/admin/purchorders";

		List<Purchaseorder> orders = purchaseorderService.getAll();
		model.addAttribute("orders", orders);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "purchorders");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/purchorder"})
	public String dspPurchaseOrder(ModelMap model, HttpSession httpSession) {
		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		String rtn = "store/admin/purchorder";

		List<Supplier> suppliers = supplierService.getAll();

		List<User> users = userService.getByRolename("ADMIN");

		model.addAttribute("users", users);
		model.addAttribute("suppliers", suppliers);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "purchorders");

		return rtn;
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/purchorder/{orderid}"})
	public String updatePurchaseOrder(ModelMap model, @PathVariable("orderid") Long orderid, HttpSession httpSession) {
		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		String rtn = "store/admin/purchorder";

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
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "purchorders");

		return rtn;
	}

	@RequestMapping(method={RequestMethod.POST}, value={"/purchorder"})
	public String doPurchaseOrder(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession)
		throws Exception {

		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
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
		if(sentdate != null && !sentdate.isEmpty()) {
			Calendar rcdCal = Calendar.getInstance();
			rcdCal.setTime(formatter.parse(sentdate));
			order.setSentdate(rcdCal);
		}

		order.setComments(comments);
		order.setSentdate(cal);
		order.setRecordedby(loginUser);
		order.setRecorddate(cal);

		purchaseorderService.save(order);

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "purchorders");

		return "redirect:" + rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/shipments"})
	public String dspShipments(ModelMap model, HttpSession httpSession) {
		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		String rtn = "store/admin/shipments";

		List<Shipment> mfs = shipmentService.getAll();
		model.addAttribute("shipments", mfs);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "shipments");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/shipment"})
	public String dspShipment(ModelMap model, HttpSession httpSession) {
		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		String rtn = "store/admin/shipment";

		List<Purchaseorder> orders = purchaseorderService.getAll();
		List<Supplier> suppliers = supplierService.getAll();

		model.addAttribute("orders", orders);
		model.addAttribute("suppliers", suppliers);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "shipments");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/shipment/supplier/{supplierid}"})
	public String loadShipment(ModelMap model, @PathVariable("supplierid") Long supplierid, HttpSession httpSession) {
		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		String rtn = "store/admin/shipment";

		long mfid = supplierid.longValue();
		System.out.println("++++++++++++++++++");
		List<Purchaseorder> orders = purchaseorderService.getUnusedOrdersBySupplierid(mfid);
		System.out.println("++++++++++++++++++");

		List<Supplier> suppliers = supplierService.getAll();
		//List<Item> items = itemService.getByShipment(mfid);

		//model.addAttribute("items", items);
		Shipment shipment = new Shipment();
		Supplier supplier = supplierService.getById(supplierid);
		shipment.setSupplier(supplier);
		model.addAttribute("orders", orders);
		model.addAttribute("suppliers", suppliers);
		model.addAttribute("shipment", shipment);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "shipments");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/shipment/{shipmentid}"})
	public String updateShipment(ModelMap model, @PathVariable("shipmentid") Long shipmentid, HttpSession httpSession) {
		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		String rtn = "store/admin/shipment";

		long mfid = shipmentid.longValue();
		Shipment mf = shipmentService.getById(mfid);

		List<Supplier> suppliers = supplierService.getAll();
		List<Item> items = itemService.getByShipment(mfid);
		System.out.println("++++++++++++++++++");
		List<Purchaseorder> orders = purchaseorderService.getUnusedOrdersBySupplierid(mf.getSupplier().getSupplierid());
		System.out.println("++++++++++++++++++");

		model.addAttribute("orders", orders);
		model.addAttribute("items", items);
		model.addAttribute("suppliers", suppliers);
		model.addAttribute("shipment", mf);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "shipments");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/shipment"})
	public String doShipment(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession)
		throws Exception {

		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		String rtn = "shipments";

		logger.info("doShipment is call!!!!! - " + loginUser.getUsername());

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
		mf.setRecordedby(loginUser);
		mf.setRecorddate(cal);
		try {
		shipmentService.save(mf);
		} catch(Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "shipments");

		return "redirect:" + rtn;
	}

	@RequestMapping(method={RequestMethod.GET}, value={"/items"})
	public String dspItems(ModelMap model, HttpSession httpSession) {
		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		String rtn = "store/admin/items";

		List<Item> items = itemService.getAll();
		model.addAttribute("items", items);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "items");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/item"})
	public String dspItem(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {

		logger.info("dspItem is called");
		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		Store store = staffService.getByUser(loginUser).getStore();
		String rtn = "store/admin/item";
		String orderid = requestParams.get("orderid");
		String shipmentid = requestParams.get("shipmentid");
		List<Purchaseorder> orders = new ArrayList<>();
		List<Shipment> shipments = new ArrayList<>();
		List<Product> products = new ArrayList<>();

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
		model.addAttribute("orderid", orderid);
		model.addAttribute("regions", regions);
		model.addAttribute("products", products);
		model.addAttribute("locations", locations);
		model.addAttribute("shipments", shipments);
		model.addAttribute("shipmentid", shipmentid);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "items");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/item/{itemid}"})
	public String updateItem(ModelMap model, @PathVariable("itemid") Long itemid, HttpSession httpSession) {
		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		String rtn = "store/admin/item";

		Store store = staffService.getByUser(loginUser).getStore();

		long mfid = itemid.longValue();
		Item item = itemService.getById(mfid);

		List<Shipment> shipments = shipmentService.getAll();
		List<Product> products = productService.getAll();
		List<Size> sizes = sizeService.getAll();
		List<Region> regions = regionService.getAll();

		List<Store> stores = new ArrayList<>();
		stores.add(store);
		//List<Store> stores = storeService.getAll();
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
		model.addAttribute("locations", locations);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("inventories", inventories);
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

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);

		Store store = staffService.getByUser(loginUser).getStore();
		String rtn = "item";

		logger.info("doShipment is call!!!!! - " + loginUser.getUsername());

		long qty = 0;
		long itmid = 0;
		String productname = requestParams.get("name");
		String itemid = requestParams.get("itemid");
		String sku = requestParams.get("sku");
		String sizeid = requestParams.get("sizeid");
		String quantity = requestParams.get("quantity");
		String cost = requestParams.get("cost");
		String orderid = requestParams.get("orderid");
		String shipmentid = requestParams.get("shipmentid");
		String comments = requestParams.get("comments");
		String locationid = requestParams.get("locationid");
		String addInventory = requestParams.get("addInventory");
		String returnurl = requestParams.get("return");

		String rtn_orderid = requestParams.get("rtn_orderid");
		String rtn_shipmentid = requestParams.get("rtn_shipmentid");
		Message msg = new Message();

		logger.info("sizeid is: "+sizeid);
		Calendar cal = Calendar.getInstance();

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
			}

			if(rtn_shipmentid != null && !rtn_shipmentid.isEmpty()) {
				rtn = "shipment/" + rtn_shipmentid;
			}
			if(returnurl.equals("item")) {
				return "redirect:item";
			}
		}

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "items");

		if(itmid == 0) {
			return "store/admin/item";
		} else {
			return "redirect:" + rtn + "/" + itmid;
		}
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/inventorys"})
	public String dspInventorys(ModelMap model, HttpSession httpSession) {
		String rtn = "store/admin/inventorys";

		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);

		List<Inventory> inventorys = inventoryService.getAll();
		model.addAttribute("inventorys", inventorys);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "inventories");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/inventory"})
	public String dspInventory(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);

		Store store = staffService.getByUser(loginUser).getStore();
		List<Store> stores = new ArrayList<>();
		stores.add(store);
		//String rtn = "store/admin/inventory";
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
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "inventories");

		return rtn;
	}


	@RequestMapping(method={RequestMethod.GET}, value={"/inventory/{inventoryid}"})
	public String updateInventory(@RequestParam Map<String,String> requestParams, ModelMap model, @PathVariable("inventoryid") Long inventoryid, HttpSession httpSession) {
		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);

		String rtn = "redirect:item/";

		//String itemid = requestParams.get("itemid");
		//Item item = itemService.getById(Long.parseLong(itemid));

		Inventory inv = inventoryService.getById(inventoryid);
		//inventoryService.delete(inventoryid);
		Item item = inv.getItem();

		List<Inventory> inventories = inventoryService.getByItem(item);
		long sum = 0;
		for(Inventory i: inventories) {
			sum += i.getQuantity();
		}

		model.addAttribute("sum", sum);
		model.addAttribute("inventories", inventories);
		//Inventory inventory = inventoryService.getById(inventoryid);

		//model.addAttribute("inventory", inventory);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "inventories");

		return rtn + item.getItemid();
	}


	@RequestMapping(method={RequestMethod.POST}, value={"/inventory"})
	public String doInventory(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession)
		throws Exception {

		logger.info("!!!!! doInventory is called");
		String rtn = "redirect:item/" ;

		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);

		String itemid = requestParams.get("itemid");
		String storeid = requestParams.get("storeid");
		String statusid = requestParams.get("statusid");
		String quantity = requestParams.get("quantity");
		String locationid = requestParams.get("locationid");
		String uinventoryid = requestParams.get("uinventoryid");
		Inventory inventory = new Inventory();
logger.info("uinventoryid: "+uinventoryid);
		if(uinventoryid != null) {
			storeid = requestParams.get("ustoreid");
			quantity = requestParams.get("uquantity");
			locationid = requestParams.get("ulocationid");
			inventory = inventoryService.getById(Long.parseLong(uinventoryid));
		}

		Calendar cal = Calendar.getInstance();
		Item item = itemService.getById(Long.parseLong(itemid));
		Store store = storeService.getById(Long.parseLong(storeid));
		Status status = statusService.getById(Long.parseLong(statusid));
		Location location = locationService.getById(Long.parseLong(locationid));

		inventory.setItem(item);
		inventory.setStore(store);
		inventory.setStatus(status);
		inventory.setLocation(location);
		inventory.setQuantity(Long.parseLong(quantity));
		inventory.setReceivedby(loginUser);
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
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Inventories");
		model.addAttribute("subMenu", "inventories");

		rtn += itemid;

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
            user = userService.findByUsername("");
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
