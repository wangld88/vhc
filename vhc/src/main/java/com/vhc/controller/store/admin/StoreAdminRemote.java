package com.vhc.controller.store.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.vhc.controller.store.StoreBase;
import com.vhc.dto.CustomerDTO;
import com.vhc.dto.InventoryDTO;
import com.vhc.dto.OrderDTO;
import com.vhc.util.Message;
import com.vhc.core.model.Account;
import com.vhc.core.model.Address;
import com.vhc.core.model.City;
import com.vhc.core.model.Customer;
import com.vhc.core.model.Giftcard;
import com.vhc.core.model.Inventory;
import com.vhc.core.model.Item;
import com.vhc.core.model.Order;
import com.vhc.core.model.Product;
import com.vhc.core.model.Promocode;
import com.vhc.core.model.Role;
import com.vhc.core.model.Size;
import com.vhc.core.model.Staff;
import com.vhc.core.model.Status;
import com.vhc.core.model.Store;
import com.vhc.core.model.User;
import com.vhc.core.model.Userrole;


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

		if(typeid != null && !typeid.trim().isEmpty()) {
			sizes = sizeService.getByTypeAndSizenum(Long.parseLong(typeid), size + "%");
		} else {
			sizes = sizeService.getBySizenum(size + "%");
		}

		logger.info("Size autocomplete Search Result: " + sizes.size());

		return sizes;
	}


	@RequestMapping(method=RequestMethod.POST, value="/searchCustomers")
	public List<CustomerDTO> getCustomers(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		logger.info("AJAX Customer search is called");

		String lastname = requestParams.get("lastname");

		//Message msg = new Message();
		List<Customer> customers = new ArrayList<>();

		if(lastname == null || lastname.isEmpty()) {
			customers = customerService.getAll();
		} else {
			User user = new User();
			user.setLastname(lastname);

			customers = customerService.getByLastnamePhone(user);
		}

		List<CustomerDTO> dto = new ArrayList<CustomerDTO> ();
		for(Customer c : customers) {
			dto.add(new CustomerDTO(c));
		}
		logger.info("Size autocomplete Search Result: " + customers.size());

		return dto;
	}


	@RequestMapping(method=RequestMethod.POST, value="/customerHistory/{customerid}")
	public List<OrderDTO> getCustomerHistory(@PathVariable Long customerid, ModelMap model, HttpSession httpSession) {

		//Customer customer = customerService.getById(customerid);
		List<Order> orders = orderService.getByCustomer(customerid);

		List<OrderDTO> dtos = new ArrayList<>();

		for(Order order: orders) {
			OrderDTO dto = new OrderDTO(order);
			dtos.add(dto);
		}

		return dtos;//orders.stream().forEach(action);;
	}


	@PostMapping("/addCustomer")
	public Customer addCustomer(@RequestParam Map<String,String> requestParams,
			ModelMap model, HttpSession httpSession) {
		// create customer
		Customer customer = new Customer();

		// create user
		String username = requestParams.get("username");
		String firstname = requestParams.get("firstname");
		String lastname = requestParams.get("lastname");
		String gender = requestParams.get("gender");
		String phone = requestParams.get("phone");
		String cell = requestParams.get("cell");
		String street = requestParams.get("street");
		String cityid = requestParams.get("cityid");
		String postalcode = requestParams.get("postalcode");
		String createdby = requestParams.get("createdby");
		Calendar cal = Calendar.getInstance();

		// create address
		Address ads = new Address();
		City city = cityService.getById(Long.parseLong(cityid));
		ads.setCity(city);
		ads.setStreet(street);
		ads.setPostalcode(postalcode);

		User createUser = userService.getById(Long.parseLong(createdby));

		if(username == null || username.isEmpty()) {
			username = cell + "@vhc.ca";
		}

		User user = userService.getByUsername(username);

		if(user != null && user.getUserid() != 0) {
			logger.error("Username ({}) already exists, please try a different one", username);
			customer.setComments("The email already exists, please try again.");
		} else {
			user = new User();

			user.setUsername(username);
			user.setFirstname(firstname);
			user.setLastname(lastname);
			if(gender != null && !gender.isEmpty()) {
				user.setGender(gender);
			}
			user.setEmail(username);
			if(phone != null && !phone.isEmpty()) {
				user.setPhone(phone);
			} else {
				user.setPhone(cell);
			}
			user.setCell(cell);
			user.setCreationdate(cal);

			try {
				logger.info("Customer doSignup");

				customer.setUser(user);
				user = userService.save(user);

				user.setCreatedby(createUser);
				user = userService.save(user);
				logger.info("[Store adm] Saved User ID: {}", user.getUserid());

				Role role = roleService.getByName("CUSTOMER");
				Userrole ur = new Userrole();
				ur.setUser(user);
				ur.setRole(role);
				ur = userroleService.save(ur);
				logger.info("[Store adm] Saved User Role ID : {}"+ur.getUserroleid());
				
				//user = userService.getById(user.getUserid());

				ads = addressService.save(ads);

				customer.setAddress(ads);
				customer.setUser(user);
				customer.setCreatedby(createUser);
				customer.setCreationdate(cal);
				customer.setUpdatedate(cal);
				customer.setUpdatedby(user);

				customer = customerService.save(customer);

				logger.info("[Store adm] customer created ID: {}", customer.getCustomerid());
				
				// create account
				Account acct = new Account();
				Status status = statusService.getByNameAndReftbl("Active", "general");

				acct.setCustomer(customer);
				acct.setStatus(status);
				acct.setCreatedby(user);
				acct.setCreationdate(cal);

				acct = accountService.save(acct);
				
				logger.info("[Store adm] account created ID: {}", acct.getAccountid());

			} catch(Exception e) {
				e.printStackTrace();
				customer.setComments("Error occurred when adding new customer, please check with system admin.");
				Throwable cause = e.getCause();
				String message = cause.getMessage();

				while(cause != null && cause.getMessage() != null && !cause.getMessage().isEmpty()) {
					message = cause.getMessage();
					cause = cause.getCause();
				}

				logger.error("Error occurred when adding new customer: {}", e.getMessage());
				//msg.setStatus(Message.ERROR);
				//msg.setMessage("An error occurred which you sign up, root cause: " + message);
			}

		}
		return customer;
	}
	/**
	 *
	 * @param requestParams
	 * @param model
	 * @param httpSession
	 * @return percentage of the promotion code,
	 * 			0 - promotion code expired
	 * 			-1 - no such promotion code
	 */
	@RequestMapping(method=RequestMethod.POST, value="/promocode")
	public long getPromotion(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		long rtn = 0;

		String code = requestParams.get("promocode");
		System.out.println("Code: "+code);
		Promocode p = promocodeService.getByCode(code);
		Calendar cal = Calendar.getInstance();

		if(p == null) {
			rtn = -1;
		} else if( cal.after(p.getStartdate()) && cal.before(p.getEnddate())) {
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


	@RequestMapping(method=RequestMethod.POST, value="/sales/add")
	public InventoryDTO addSale(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {

		logger.info("[ST ADM] Point of sales add is called");

		String staffid = requestParams.get("staffid");
		Staff staff = staffService.getById(Long.parseLong(staffid));
		Store store = staff.getStore();
		Status received = statusService.getByNameAndReftbl("Received", "inventories");
		Status returned = statusService.getByNameAndReftbl("Returned", "inventories");

		requestParams.forEach((k, v) -> System.out.println(k + " : " + (v)));

		String sku = requestParams.get("sku");

		List<Status> statuss = new ArrayList<>();
		statuss.add(received);
		statuss.add(returned);

		logger.info("[ST ADM] sku: "+sku+", storeid: "+store.getStoreid()+", status: "+received.getStatusid());
		List<Inventory> inventories = inventoryService.getByStoreAvaiableUPC(sku, store.getStoreid(), statuss); //received);

		if(inventories != null && !inventories.isEmpty()) {
			logger.info("[ST ADM] Point Sale inventories: {}, selected itemid: {}", inventories.size(), inventories.get(0).getItem().getItemid());
			
			Object mutex = WebUtils.getSessionMutex(httpSession);
			Inventory current = null;
					
			synchronized(mutex) {
				List<Inventory> saleList = null;
				if(httpSession.getAttribute("saleList") != null) {
					saleList = (List<Inventory>) httpSession.getAttribute("saleList");
				} else {
					saleList = new ArrayList<>();
				}

				logger.info("[ST ADM] inventory is found:"+inventories.size()+", sku: "+sku);

				if(inventories.size() > 1) {
					inventories = removeAdded(inventories, saleList);
				}
				
				if(inventories.isEmpty()) {
					logger.error("[ST ADM] There is no inventory available.");
					return null;
				} else {
					current = inventories.get(0);
	
					if(isSkuNotSelected(saleList,current)) {
						saleList.add(current);
						httpSession.setAttribute("saleList", saleList);
					} else {
						logger.info("[ST ADM] The item (" + sku + ") has been added already.");
					}
				}
			}
			return new InventoryDTO(current);
		}

		return null;
	}

	
	@RequestMapping(method=RequestMethod.POST, value="/sales/remove")
	public void removeSale(@RequestBody String json, 
			HttpServletRequest request, ModelMap model, HttpSession httpSession) {
		logger.info("Product sales remove is called");

		JSONObject obj = new JSONObject(json);
		String staffid = obj.getString("staffid");
		Long inventoryid = obj.getLong("inventoryid");
		
		if(staffid != null && !staffid.isEmpty() && inventoryid != null) {
			Staff staff = staffService.getById(Long.parseLong(staffid));
			Store store = staff.getStore();
			
			Object mutex = WebUtils.getSessionMutex(httpSession);
			synchronized(mutex) {
				List<Inventory> saleList = null;
				Inventory inv = inventoryService.getById(inventoryid);
				if(inv.getStore().equals(store)) {
					if(httpSession.getAttribute("saleList") != null) {
						saleList = (List<Inventory>) httpSession.getAttribute("saleList");
						for(Inventory i : saleList) {
							if(i.getInventoryid() == inventoryid) {
								saleList.remove(i);
								logger.info("[ST ADM] The inventory item {} is removed from shopping cart.", i.getInventoryid());
								break;
							}
						}
						httpSession.setAttribute("saleList", saleList);
					} else {
						logger.error("[ST ADM] The shopping cart is empty, can't remove the item.");
					}
				} else {
					logger.error("[ST ADM] The remove item does not belong to this store {}.", store.getStoreid());
				}
			}
		} else {
			logger.error("[ST ADM] Passed parameters are invalid!");
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/api/inventories/{storeid}")
	public @ResponseBody DataTablesOutput<Inventory> getInventories(
			@PathVariable Long storeid,
			@Valid @RequestBody DataTablesInput input) {
		System.out.println("DataTablesOutput");
		logger.info("[SAdm API]Load inventory table is called, storeid: {}", storeid);
		Store store = storeService.getById(storeid);

		Status received = statusService.getByNameAndReftbl("Received", "inventories");
		Status returned = statusService.getByNameAndReftbl("Returned", "inventories");
		Status transfered = statusService.getByNameAndReftbl("Transferred", "inventories");

		List<Status> statuss = new ArrayList<>();
		statuss.add(received);
		statuss.add(returned);
		statuss.add(transfered);

		return inventoryService.getAll(input, store, statuss, transfered);

	}


	@RequestMapping(method=RequestMethod.POST, value = "/api/items/{storeid}")
	public @ResponseBody DataTablesOutput<Item> getItems(
			@PathVariable Long storeid,
			@Valid @RequestBody DataTablesInput input) {
		System.out.println("getItems DataTablesOutput============");
		logger.info("[SAdm API] Load item table is called, storeid: {}", storeid);
		Store store = storeService.getById(storeid);

		return itemService.getAllByStore(input, store);

	}


	private List<Inventory> removeAdded(List<Inventory> target, List<Inventory> saleList) {
		/*List<Inventory> inventories = new ArrayList<>();

		for(Inventory inv : saleList) {
			for(Inventory t : target) {
				if(inv.getInventoryid() != t.getInventoryid()) {

				}
			}
		}*/
		List<Inventory> result = target.stream().filter(inv -> saleList.stream().allMatch(t -> inv.getInventoryid() != t.getInventoryid())).collect(Collectors.toList());

		return result;
	}

	private boolean isSkuNotSelected(List<Inventory> saleList, Inventory inventory) {

		List<Inventory> result = saleList.stream().filter(inv -> inv.getInventoryid() == inventory.getInventoryid()).collect(Collectors.toList());

		return result.isEmpty();
	}

}