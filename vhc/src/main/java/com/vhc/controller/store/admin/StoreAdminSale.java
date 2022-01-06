package com.vhc.controller.store.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.vhc.controller.store.StoreBase;
import com.vhc.dto.OrderitemDTO;
import com.vhc.core.model.Account;
import com.vhc.core.model.City;
import com.vhc.core.model.Creditcard;
import com.vhc.core.model.Customer;
import com.vhc.core.model.Debitcard;
import com.vhc.core.model.Giftcard;
import com.vhc.core.model.GiftcardHistory;
import com.vhc.core.model.Inventory;
import com.vhc.core.model.Invoice;
import com.vhc.core.model.Item;
import com.vhc.core.model.Order;
import com.vhc.core.model.Orderitem;
import com.vhc.core.model.Payment;
import com.vhc.core.model.Paymentdetail;
import com.vhc.core.model.Paymentmethod;
import com.vhc.core.model.Product;
import com.vhc.core.model.Promocode;
import com.vhc.core.model.Staff;
import com.vhc.core.model.Status;
import com.vhc.core.model.Store;
import com.vhc.core.model.Transaction;
import com.vhc.core.model.Type;
import com.vhc.core.model.User;
import com.vhc.security.LoginUser;
import com.vhc.util.BarcodeProcessor;
import com.vhc.util.Message;
import com.vhc.util.PrintEngine;


@Controller
@RequestMapping("/store/admin")
public class StoreAdminSale extends StoreBase {

	private final static Logger logger = LoggerFactory.getLogger(StoreAdminSale.class);

	private final static long TAX_GENERAL = 13;

	private final static long TAX_KID = 5;


	@RequestMapping(method=RequestMethod.GET, value="/onlinesales")
	public String dspOnlineSales(ModelMap model, @ModelAttribute("customer") Customer customer, HttpSession httpSession) {
		String rtn = "store/admin/onlinesales";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		List<Staff> staffs = staffService.getByStore(store);
		Status completed = statusService.getByNameAndReftbl("Completed", "orders");
		List<Order> orders = orderService.getUnprocessedOnlines(); //getIncompletedOnlines(completed);

		logger.info("[Store] orders: {}", orders.size());

		model.addAttribute("store", store);
		model.addAttribute("staffs", staffs);
		model.addAttribute("orders", orders);
		model.addAttribute("customer", customer);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Sales");

		return rtn;
	}

	@RequestMapping(method=RequestMethod.GET, value="/onlinesales/{orderid}")
	public String dspOnlineSale(@PathVariable("orderid") Long orderid,
			ModelMap model,
			HttpServletRequest request,
			HttpSession httpSession) {
		String rtn = "store/admin/order";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		List<Staff> staffs = staffService.getByStore(store);
		Status completed = statusService.getByNameAndReftbl("Completed", "orders");
		List<Order> orders = orderService.getUnprocessedOnlines(); //getIncompletedOnlines(completed);

		logger.info("[Store] orders: {}", orders.size());

		model.addAttribute("store", store);
		model.addAttribute("staffs", staffs);
		model.addAttribute("orders", orders);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Sales");

		return rtn;
	}

	@RequestMapping(method=RequestMethod.GET, value="/sales")
	public String dspSales(ModelMap model, @ModelAttribute("customer") Customer customer, HttpSession httpSession) {
		String rtn = "store/admin/sales";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}
		
		Object mutex = WebUtils.getSessionMutex(httpSession);
		synchronized(mutex) {
			List<Inventory> saleList = null;
			httpSession.removeAttribute("saleList");
			httpSession.removeAttribute("invoiceList");
			model.addAttribute("saleList", saleList);
		}

		Store store = staff.getStore();

		List<Staff> staffs = staffService.getByStore(store);
		List<Type> types = typeService.getByReftbl("transactions");

		List<Paymentmethod> methods = paymentmethodService.getAll();
		List<City> cities = cityService.getAll();

		model.addAttribute("cities", cities);
		model.addAttribute("methods", methods);
		model.addAttribute("store", store);
		model.addAttribute("staffs", staffs);
		model.addAttribute("types", types);
		model.addAttribute("customer", customer);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Sales");

		return rtn;
	}

	@RequestMapping(method=RequestMethod.GET, value="/itemsale")
	public String dspItemSale(ModelMap model, @ModelAttribute("customer") Customer customer, HttpSession httpSession) {
		String rtn = "store/admin/itemsale";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		//Status received = statusService.getByName("Received");

		//Store store = staffService.getByUser(loginUser).getStore();

		//List<Inventory> avaialbe = inventoryService.getByStoreAvaiable(store.getStoreid(), received);

		Object mutex = WebUtils.getSessionMutex(httpSession);

		synchronized(mutex) {
			List<Inventory> saleList = null;
			httpSession.removeAttribute("saleList");
			httpSession.removeAttribute("invoiceList");
			//httpSession.setAttribute("saleList", saleList);
			//httpSession.setAttribute("invoiceList", null);
			model.addAttribute("saleList", saleList);
		}

		//model.addAttribute("avaialbe", avaialbe);

		model.addAttribute("store", store);
		model.addAttribute("customer", customer);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Sales");

		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(method=RequestMethod.GET, value="/itemsale/add")
	public String addItemSale(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "store/admin/itemsale";

		logger.info("product sale add is called");
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		Status received = statusService.getByNameAndReftbl("Received", "inventories");
		Status returned = statusService.getByNameAndReftbl("Returned", "inventories");

		List<Status> statuss = new ArrayList<>();
		statuss.add(received);
		statuss.add(returned);
		String sku = requestParams.get("sku");
		logger.info("sku: "+sku+", storeid: "+store.getStoreid()+", status: "+received.getStatusid());
		List<Inventory> inventories = inventoryService.getByStoreAvaiableUPC(sku, store.getStoreid(), statuss); //received);

		Message msg = new Message();

		if(inventories != null && !inventories.isEmpty()) {
			logger.info("inventories found: " + inventories.get(0).getItem().getItemid());

			Object mutex = WebUtils.getSessionMutex(httpSession);

			synchronized(mutex) {
				List<Inventory> saleList = null;
				if(httpSession.getAttribute("saleList") != null) {
					saleList = (List<Inventory>) httpSession.getAttribute("saleList");
					//subTotal = calTotal(saleList);
				} else {
					saleList = new ArrayList<>();
				}

				/*if(inventories != null && !inventories.isEmpty()) {*/
				logger.info("inventory is found:"+inventories.size()+", sku: "+sku);

				if(inventories.size() > 1) {
					inventories = removeAdded(inventories, saleList);
				}

				logger.info("inventory is found:"+inventories.size()+", sku: "+sku);

				if(inventories.isEmpty()) {
					logger.error("[Store Adm] There is no inventory available.");
					msg.setStatus(Message.ERROR);
					msg.setMessage("No item being found, please try a different UPC");
				} else {
					Inventory current = inventories.get(0);
	
					if(isSkuNotSelected(saleList, current)) {
						saleList.add(current);
						////subTotal = subTotal.add(current.getItem().getProduct().getFinalprice());
						logger.info("[Sale] items in cart: "+saleList.size());
						httpSession.setAttribute("saleList", saleList);
					} else {
						msg.setStatus(Message.ERROR);
						msg.setMessage("The item (" + sku + ") has been added already.");
					}
					/*} else {
						msg.setStatus(Message.ERROR);
						msg.setMessage("Can not find the given item in inventories.");
					}*/
					model.addAttribute("saleList", saleList);
	
					processCart(model, saleList, null);
				}
			}

		} else {
			msg.setStatus(Message.ERROR);
			msg.setMessage("No item being found, please try a different UPC");
		}

		List<Paymentmethod> methods = paymentmethodService.getAll();

		model.addAttribute("methods", methods);
		/*model.addAttribute("subTotal", subTotal);
		model.addAttribute("tax",tax);
		model.addAttribute("total",total);*/
		model.addAttribute("message", msg);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Sales");

		return rtn;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(method=RequestMethod.GET, value="/itemsale/remove/{inventoryid}")
	public String removeItemSale(@PathVariable("inventoryid") Long inventoryid,
			ModelMap model,
			HttpSession httpSession) {

		String rtn = "store/admin/itemsale";

		logger.info("product sale remove is called, inventoryid is {}", inventoryid);
		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		Message msg = new Message();

		Object mutex = WebUtils.getSessionMutex(httpSession);

		synchronized(mutex) {
			if(httpSession.getAttribute("saleList") != null) {
				List<Inventory> saleList = (List<Inventory>) httpSession.getAttribute("saleList");
				List<Inventory> newList = saleList.stream().filter(inv -> inv.getInventoryid() != inventoryid).collect(Collectors.toList());
				logger.info("Size of old: {}, size of new {}", saleList.size(), newList.size());
				httpSession.setAttribute("saleList", newList);

				processCart(model, newList, null);
				msg.setStatus(Message.SUCCESS);
				msg.setMessage("The item has been removed successfully.");
				model.addAttribute("saleList", newList);
			} else {
				msg.setStatus(Message.ERROR);
				msg.setMessage("There is no item to be removed.");
				model.addAttribute("message", msg);
			}

		}

		List<Paymentmethod> methods = paymentmethodService.getAll();

		model.addAttribute("message", msg);
		model.addAttribute("methods", methods);
		model.addAttribute("loginUser", loginUser);

		return rtn;
	}


	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor=Exception.class)
	@RequestMapping(method=RequestMethod.POST, value="/itemsale")
	public String doItemSale(@RequestParam(name="methodid", required=false) String[] methodid,
		@RequestParam Map<String,String> requestParams,
		ModelMap model,
		HttpSession httpSession) {

		logger.info("doItemSale is called");
		String rtn = "store/admin/itemsale";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Sales");

		Status delivered = statusService.getByName("Delivered");

		List<Inventory> inventories = new ArrayList<>();

		String cardcode = requestParams.get("cardcode");
		String cardnum = requestParams.get("cardnum");
		String pin = requestParams.get("pin");
		String code = requestParams.get("promocode");
		String dsct = requestParams.get("discount");

		Promocode p = promocodeService.getByCode(code);

		Account account = new Account();
		Payment payment = new Payment();
		Invoice invoice = new Invoice();
		Transaction trx = new Transaction();

		if(methodid != null && methodid.length != 0) {

			List<String> methodids = Arrays.asList(methodid);

			try {

				if( !methodids.contains("1") && !methodids.contains("2") && !methodids.contains("3") && !methodids.contains("4")) {
					//invalid payment method
					Message msg = new Message();
					msg.setStatus(Message.ERROR);
					msg.setMessage("Invilad payment method.");
					model.addAttribute("message", msg);
					return "redirect:/store/admin/itemsale"; // Why return -1???
				}
				logger.info("[StoAdmin Sale] itemsale, Payment method: {}", methodids);

				Giftcard giftcard = null;

				if(cardcode != null && !cardcode.isEmpty()) {
					if(pin != null && !pin.isEmpty()) {
						giftcard = giftcardService.getByCodePin(cardcode, pin);

						/*if(giftcard != null && giftcard.getBalance().compareTo(BigDecimal.ZERO) == 0) {
							Message msg = new Message();
							msg.setStatus(Message.ERROR);
							msg.setMessage("Current balance is zero.");
							model.addAttribute("message", msg);
							return "-1";
						}*/
					} else {
						giftcard = giftcardService.getByCode(cardcode);
						/*Message msg = new Message();
						msg.setStatus(Message.ERROR);
						msg.setMessage("Pin number is required.");
						model.addAttribute("message", msg);
						return "-1";*/
					}

					if(giftcard != null && giftcard.getBalance().compareTo(BigDecimal.ZERO) == 0) {
						Message msg = new Message();
						msg.setStatus(Message.ERROR);
						msg.setMessage("Current balance is zero.");
						model.addAttribute("message", msg);
						logger.error("Current Giftcard ({}) balance is zero.", giftcard.getGiftcardid());
						return "redirect:/store/admin/itemsale";
					}
				}

				logger.info("[StoAdmin Sale] itemsale, doItemSale is called");

				Object mutex = WebUtils.getSessionMutex(httpSession);

				synchronized(mutex) {
					List<Inventory> saleList = null;
					if(httpSession.getAttribute("saleList") != null) {
						saleList = (List<Inventory>) httpSession.getAttribute("saleList");
						if(saleList == null || saleList.isEmpty()) {
							logger.error("[StoAdmin Sale] itemsale, There is no sale item, current loginUser ID {}", loginUser.getUserid());
							Message msg = new Message();
							msg.setStatus(Message.ERROR);
							msg.setMessage("Please add shopping item, now the cart is empty.");
							model.addAttribute("message", msg);
							return "redirect:/store/admin/itemsale";
						}

						double total = 0.0;
						BigDecimal discount = BigDecimal.ZERO;

						Customer customer = customerService.getById(1);

						Order order = new Order();
						order.setStore(store);
						order.setStaff(staff);
						order.setCreatedby(loginUser);
						order.setCreationdate(Calendar.getInstance());
						order.setCustomer(customer);

						if(p != null && p.getPromocodeid() != 0) {
							order.setPromocode(p);
						}

						if(dsct != null && !dsct.isEmpty()) {
							discount = convertAmount(dsct);
							order.setDiscount(discount);
						}
						order = orderService.save(order);

						Type type = typeService.getByNameReftbl("Sell", "transactions");
						GiftcardHistory gfHistory = new GiftcardHistory();

						logger.info("[StoAdmin Sale] itemsale, inventory list size: {}", saleList.size());
						for(Inventory inventory: saleList) {
							inventory.setStatus(delivered);
							inventory.setSentby(loginUser);
							inventory.setSenddate(Calendar.getInstance());
							inventories.add(inventoryService.save(inventory));

							Orderitem otm = new Orderitem();
							Item item = inventory.getItem();
							Product prod = item.getProduct();

							otm.setOrder(order);
							otm.setItem(item);
							otm.setQuantity(1);

							double amt = prod.getFinalprice().doubleValue();

//System.out.println("amount before : "+amt);
							if(p != null && p.getPromocodeid() != 0) {
								otm.setPromocode(p);
								amt = amt - amt * p.getPercentage() / 100;
							}

							otm.setAmount(new BigDecimal(amt));

//System.out.println("amount after promotion: "+amt+", tax: "+TAX_GENERAL/100);

							if(item.getSize().getType().getName().equals("Kids")) {
								otm.setTax(TAX_KID);
								amt = amt + amt * TAX_KID / 100;
							} else {
								otm.setTax(TAX_GENERAL);
								amt = amt + amt * TAX_GENERAL /100;
							}

//System.out.println("amount after tax: "+amt);
							total += amt;
							otm.setType(type);
							orderitemService.save(otm);
						}
//System.out.println("Total: "+total);
						BigDecimal amount = new BigDecimal(total);
						amount = amount.subtract(discount);
						amount.setScale(2, BigDecimal.ROUND_HALF_UP);

						order.setAmount(amount);

						order = orderService.save(order);

						httpSession.setAttribute("order", order);

						account = accountService.getById(1); //ByCustomer(customer)

						payment.setAmount(amount);
						payment.setCreatedby(loginUser);
						payment.setCreationdate(Calendar.getInstance());

						payment = paymentService.save(payment);

						BigDecimal sum = BigDecimal.ZERO;
						sum.setScale(2, BigDecimal.ROUND_HALF_UP);

						List<Paymentdetail> details = new ArrayList<>();
						Status status = statusService.getByNameAndReftbl("Completed","payments");
						logger.info("[StoAdmin Sale] itemsale, Payment method: {}", methodids);

						//multiple payment methods
						if(methodids.contains("1")) {
							logger.info("[StoAdmin Sale] itemsale, Credit card payment");
							BigDecimal credit = convertAmount(requestParams.get("creditamount"));
							sum = sum.add(credit);

							Creditcard creditcard = new Creditcard(cardnum, customer);
							creditcard = creditcardService.save(creditcard);
							Paymentdetail detail = new Paymentdetail();
							Paymentmethod method = paymentmethodService.getById(1);
							detail.setAmount(credit);
							detail.setPaymentmethod(method);
							detail.setRefnum("" + creditcard.getCreditcardid());
							detail.setPaydate(Calendar.getInstance());
							detail.setReceived(credit);
							detail.setStatus(status);
							detail.setPayment(payment);

							details.add(detail);
						}

						if(methodids.contains("2")) {
							logger.info("[StoAdmin Sale] itemsale, Debit payment");
							BigDecimal debit = convertAmount(requestParams.get("debitamount"));
							sum = sum.add(debit);

							Debitcard debitcard = new Debitcard(cardnum, customer);
							debitcard = debitcardService.save(debitcard);
							Paymentdetail detail = new Paymentdetail();
							Paymentmethod method = paymentmethodService.getById(2);
							detail.setAmount(debit);
							detail.setPaymentmethod(method);
							detail.setRefnum("" + debitcard.getDebitcardid());
							detail.setPaydate(Calendar.getInstance());
							detail.setReceived(debit);
							detail.setStatus(status);
							detail.setPayment(payment);

							details.add(detail);
						}

						if(methodids.contains("3")) {
							System.out.println("Gift card payment: "+giftcard.getCode());
							BigDecimal gift = convertAmount(requestParams.get("giftamount"));
							sum = sum.add(gift);
							BigDecimal balance = giftcard.getBalance().subtract(gift);
							if(balance.compareTo(BigDecimal.ZERO) < 0) {
								balance = BigDecimal.ZERO;
							}
							Paymentdetail detail = new Paymentdetail();
							Paymentmethod method = paymentmethodService.getById(3);
							giftcard.setBalance(balance);
							giftcard = giftcardService.save(giftcard);
							//History
							Type gfType = typeService.getByNameReftbl("Purchase", "giftcards");
							gfHistory.setType(gfType);
							gfHistory.setGiftcard(giftcard);
							gfHistory.setAmount(gift);
							gfHistory.setBalance(balance);
							gfHistory.setOperatedate(Calendar.getInstance());
							gfHistory.setOperatedby(loginUser);
							gfHistory = giftcardHistoryService.save(gfHistory);

							detail.setRefnum(""+giftcard.getGiftcardid());
							detail.setPaymentmethod(method);
							detail.setPaydate(Calendar.getInstance());
							detail.setAmount(gift);
							detail.setReceived(gift);
							detail.setStatus(status);
							detail.setPayment(payment);

							details.add(detail);
						}

						if (methodids.contains("4")) {
							logger.info("[StoAdmin Sale] itemsale, Cash payment");
							BigDecimal cash = convertAmount(requestParams.get("cashamount"));
							Paymentmethod method = paymentmethodService.getById(4);
							BigDecimal backchange = BigDecimal.ZERO;
							Paymentdetail detail = new Paymentdetail();
							BigDecimal residual = amount.subtract(sum);
							detail.setReceived(cash);

							if(cash.compareTo(residual) > 0) {
								backchange = cash.subtract(residual);
								backchange.setScale(2, BigDecimal.ROUND_HALF_UP);
								detail.setAmount(residual);
							} else {
								detail.setAmount(cash);
							}

							sum = sum.add(residual);

							detail.setPaymentmethod(method);
							detail.setPaydate(Calendar.getInstance());
							detail.setBackchange(backchange);
							detail.setStatus(status);
							detail.setPayment(payment);

							details.add(detail);
						}

						amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
						sum = sum.setScale(2, BigDecimal.ROUND_HALF_UP);

						if(amount.compareTo(sum) != 0) {
							//Error amount are different
							logger.error("[StoAdmin Sale] itemsale, Item amount: " + amount.doubleValue()+", payment amount: "+sum.doubleValue());
							Message msg = new Message();
							msg.setStatus(Message.ERROR);
							msg.setMessage("Please add shopping item, now the cart is empty.");
							model.addAttribute("message", msg);
						}

						payment.setPaymentdetails(details);
						payment = paymentService.save(payment);

						invoice.setAmount(amount);
						invoice.setOrder(order);
						invoice.setCreatedby(loginUser);
						invoice.setCreationdate(Calendar.getInstance());
						invoice = invoiceService.save(invoice);
						String mask = "000000000";
						String invoicenum = mask.substring(0, mask.length()-String.valueOf(invoice.getInvoiceid()).length())+invoice.getInvoiceid();

						invoice.setInvoicenum(invoicenum);

						//String uuid = UUID.randomUUID().toString().substring(0,6);
						Random rand = new Random();
						String barcode = rand.nextInt(9999999) + invoicenum;

						invoice.setBarcode(barcode);

						invoice = invoiceService.save(invoice);

						trx.setType(type);
						trx.setAccount(account);
						trx.setPayment(payment);
						trx.setInvoice(invoice);
						trx.setRecordedby(loginUser);
						trx.setRecorddate(Calendar.getInstance());

						//rtn = String.valueOf(invoice.getInvoiceid());

						trx = transactionService.save(trx);

						if(gfHistory.getGfhistoryid() != 0) {
							gfHistory.setTrxn(trx);
							giftcardHistoryService.save(gfHistory);
						}

						logger.info("[StoAdmin Sale] itemsale, The transaction completed, trx ID:" + trx.getTransactionid());
						model.addAttribute("saleList", saleList);
						httpSession.setAttribute("invoice", invoice);
						httpSession.setAttribute("invoiceList", saleList);
						httpSession.removeAttribute("saleList");
					} else {
						logger.error("[StoAdmin Sale] itemsale, saleList is null");
					}
				}

			} catch(Exception e) {
				e.printStackTrace();
				logger.error("[StoAdmin Sale] itemsale, an error occurred while process the payment: {}", e.getMessage());
				Message msg = new Message();
				msg.setStatus(Message.ERROR);
				msg.setMessage("There is an error while process the payment: " + e.getMessage());
				model.addAttribute("message", msg);
				return rtn;
			}

		} else {
			Message msg = new Message();
			msg.setStatus(Message.ERROR);
			msg.setMessage("Please select the payment method.");
			model.addAttribute("message", msg);
			return rtn;
		}

		logger.debug("Sale is completed, inventory is updated as well.");

		Message msg = new Message();
		msg.setStatus(Message.SUCCESS);
		msg.setMessage("Sale is completed, inventory is updated as well.");
		model.addAttribute("message", msg);
		model.addAttribute("inventories", inventories);
		model.addAttribute("invoice", invoice);

		return rtn;
	}


	@SuppressWarnings("unchecked")
	@RequestMapping(method=RequestMethod.GET, value="/itemsale/invoice")
	public String dspInvoice(ModelMap model, HttpSession httpSession) {
		String rtn = "store/admin/itemsale";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		Object mutex = WebUtils.getSessionMutex(httpSession);
		String image = "";

		List<Inventory> saleList = null;

		synchronized(mutex) {
			if(httpSession.getAttribute("saleList") != null) {
				saleList = (List<Inventory>) httpSession.getAttribute("saleList");
			} else {
				saleList = new ArrayList<>();
			}
			httpSession.setAttribute("invoiceList", saleList);
			model.addAttribute("invoiceList", saleList);

			if(httpSession.getAttribute("invoice") != null) {
				Invoice invoice = (Invoice) httpSession.getAttribute("invoice");
				BarcodeProcessor processor = new BarcodeProcessor();
				image = processor.process(invoice.getBarcode());
				System.out.println("barcode: "+image);
			}
		}

		model.addAttribute("loginUser", loginUser);

		try {
			PrintEngine printer = new PrintEngine();
			printer.setPrintData(saleList);
			printer.print();
		} catch(Exception e) {
			logger.error("Receipt printing error: "+e.getMessage());
		}

		model.addAttribute("barcode", image);
		model.addAttribute("menu", "Sales");

		return rtn;
	}


	@SuppressWarnings("unchecked")
	@RequestMapping(method=RequestMethod.GET, value={"/itemsale/printinvoice","/itemreturn/printinvoice","/printinvoice"})
	public String dspPrintInvoice(ModelMap model, HttpSession httpSession) {

		logger.info("dspPrintInvoice is called");
		String rtn = "store/admin/invoice";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		Object mutex = WebUtils.getSessionMutex(httpSession);

		List<Inventory> saleList = new ArrayList<>();
		Invoice invoice = new Invoice();
		//BigDecimal subTotal = BigDecimal.ZERO;
		String image = "";
		Order order = null;

		synchronized(mutex) {

			if(httpSession.getAttribute("invoiceList") != null) {
				saleList = (List<Inventory>) httpSession.getAttribute("invoiceList");
System.out.println("!!!!!!!!!!!invoiceList:"+saleList.size());;
				invoice = (Invoice) httpSession.getAttribute("invoice");
				order = (Order) httpSession.getAttribute("order");
				List<Transaction> txs = transactionService.getByInvoice(invoice);
				List<Paymentdetail> payments = new ArrayList<>();
				String txType = "";

				if(txs != null && !txs.isEmpty()) {
					for(Transaction tx : txs) {
						payments.addAll(tx.getPayment().getPaymentdetails());
						txType = tx.getType().getName();
					}
				}
				model.addAttribute("order", order);
				model.addAttribute("payments", payments);
				model.addAttribute("txType", txType);

				if(httpSession.getAttribute("invoice") != null) {
					BarcodeProcessor processor = new BarcodeProcessor();
					image = processor.process(invoice.getBarcode());
//System.out.println("barcode: "+image);
				}
			}

			httpSession.setAttribute("invoiceList", saleList);

			model.addAttribute("saleList", saleList);
			model.addAttribute("invoice", invoice);
			processCart(model, saleList, order);
		}

		model.addAttribute("store", store);

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("barcode", image);

		return rtn;
	}


	@GetMapping(value="/itemreturn")
	public String dspReturn(ModelMap model, HttpSession httpSession) {
		String rtn = "/store/admin/itemreturn";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Sales");

		return rtn;
	}


	@GetMapping(value="/itemreturn/add")
	public String dspReturnItem(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "/store/admin/itemreturn";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		Status received = statusService.getByNameAndReftbl("Received", "inventories");

		Message msg = new Message();

		String barcode = requestParams.get("sku");
		logger.info("sku: "+barcode+", storeid: "+store.getStoreid()+", status: "+received.getStatusid());

		Invoice invoice = invoiceService.getByBarcode(barcode);

		if(invoice == null) {
			invoice = invoiceService.getByInvoicenum(barcode);
		}
//System.out.println("Invoice num: "+invoice.getInvoicenum());

		List<OrderitemDTO> oitmDTOs = new ArrayList<>();

		if(invoice != null) {
//System.out.println("Returned ORDER: "+invoice.getOrder().getOrderid());
			List<Orderitem> items = invoice.getOrder().getOrderitems();

			for(Orderitem oitem : items) {
				OrderitemDTO oitmDTO = new OrderitemDTO(oitem);
				Inventory inventory = inventoryService.getByItemid(oitem.getItem().getItemid());
				oitmDTO.setInventory(inventory);
				oitmDTOs.add(oitmDTO);
			}

			msg.setStatus(Message.SUCCESS);
			msg.setMessage("The invoice has been found");
		} else {
			msg.setStatus(Message.ERROR);
			msg.setMessage("The invoice info provided is not corrected");
		}

		model.addAttribute("message", msg);
		model.addAttribute("barcode", barcode);
		model.addAttribute("invoice", invoice);
		model.addAttribute("orderitems", oitmDTOs);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Sales");

		return rtn;
	}


	@PostMapping(value="/itemreturn")
	public String doReturn(
			@RequestParam(name="orderitemid", required=false) Long[] orderitemids,
			@RequestParam Map<String,String> requestParams,
			ModelMap model,
			HttpSession httpSession) {

		String rtn = "/store/admin/itemreturn";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		//Account account = accountService.getById(1); //ByCustomer(customer)

		BigDecimal total = new BigDecimal(0);

		String invoiceid = requestParams.get("invoiceid");
		Invoice invoice = invoiceService.getById(Integer.parseInt(invoiceid));

		String barcode = requestParams.get("barcode");

		//List<Orderitem> items = invoice.getOrder().getOrderitems();
		List<OrderitemDTO> oitmDTOs = new ArrayList<>();

		logger.info("orderitemids count: {}, invoice barcode: {}", orderitemids.length, invoice.getBarcode());

		if(orderitemids != null && orderitemids.length > 0) {
			List<Transaction> tx = transactionService.getByInvoice(invoice);

			if(tx.isEmpty()) {
				//to do
			} else {
				Status completed = statusService.getByNameAndReftbl("Completed","payments");
				Status returned = statusService.getByNameAndReftbl("Returned","inventories");
				Status delivered = statusService.getByNameAndReftbl("Delivered","inventories");
				Type type = typeService.getByNameReftbl("Return", "transactions");
				List<Paymentdetail> details = new ArrayList<>();
				//Customer customer = customerService.getById(1);
				List<Inventory> invoiceList = new ArrayList<>();

				Transaction trx = tx.get(0);
				Payment payment = trx.getPayment();
				List<Paymentdetail> oldDetails = payment.getPaymentdetails();
				Paymentmethod oldMethod = paymentmethodService.getById(3);
				Paymentdetail oldDetail = oldDetails.stream().filter(d -> d.getPaymentmethod().equals(oldMethod)).findAny().orElse(null);

				//create new order for return
				Order order = new Order();
				order.setStore(store);
				order.setStaff(staff);
				order.setCreatedby(loginUser);
				order.setCreationdate(Calendar.getInstance());
				order.setCustomer(trx.getAccount().getCustomer());
				order = orderService.save(order);

				//update the existing order item, mark as returned, update inventory to returned?
				for(int i=0; i<orderitemids.length; i++) {
					Orderitem oitem = orderitemService.getById(orderitemids[i]);
					Orderitem noitm = new Orderitem();
					Inventory inventory = inventoryService.getByItemid(oitem.getItem().getItemid());

					if(inventory.getStatus().equals(delivered)) {
						BigDecimal itm_tax = oitem.getAmount().multiply((new BigDecimal(oitem.getTax()))).divide(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
						BigDecimal itm_amount = itm_tax.add(oitem.getAmount());
						total = total.add(itm_amount);
						noitm.setOrder(order);
						noitm.setItem(oitem.getItem());
						noitm.setType(type);
						noitm.setAmount(oitem.getAmount().negate());
						noitm.setQuantity(oitem.getQuantity());
						noitm.setTax(oitem.getTax());
						noitm = orderitemService.save(noitm);

						inventory.setStatus(returned);
						inventory = inventoryService.save(inventory);
						invoiceList.add(inventory);
					}

					OrderitemDTO oitmDTO = new OrderitemDTO(oitem);
					oitmDTO.setInventory(inventory);
					oitmDTOs.add(oitmDTO);
				}

				//create payment & detail
				Payment rtn_payment = new Payment();
				Invoice rtn_invoice = new Invoice();
				Transaction rtn_trx = new Transaction();

				BigDecimal amount = total.negate();
				Paymentmethod method = paymentmethodService.getById(4);
				BigDecimal backchange = BigDecimal.ZERO;
				Paymentdetail detail = new Paymentdetail();
				//BigDecimal residual = BigDecimal.ZERO;

				rtn_payment.setAccount(trx.getAccount());
				rtn_payment.setAmount(amount);
				rtn_payment.setCreatedby(loginUser);
				rtn_payment.setCreationdate(Calendar.getInstance());

				if(oldDetail != null) {
					BigDecimal oldAmount = oldDetail.getAmount();
					BigDecimal newAmount = BigDecimal.ZERO;
					BigDecimal balance = BigDecimal.ZERO;
					BigDecimal residual = BigDecimal.ZERO;

					long giftcardid = Long.parseLong(oldDetail.getRefnum());
					Giftcard giftcard = giftcardService.getById(giftcardid);

					if(oldAmount.compareTo(total) < 0) {

						balance = giftcard.getBalance().add(oldAmount);
						newAmount = oldAmount;
						residual = total.subtract(oldAmount);

					} else {

						balance = giftcard.getBalance().add(total);
						newAmount = total;
					}

					giftcard.setBalance(balance);
					giftcard = giftcardService.save(giftcard);

					//History
					Type gfType = typeService.getByNameReftbl("Receive", "giftcards");
					GiftcardHistory gfHistory = new GiftcardHistory();

					gfHistory.setType(gfType);
					gfHistory.setGiftcard(giftcard);
					gfHistory.setAmount(newAmount);
					gfHistory.setBalance(balance);
					gfHistory.setOperatedate(Calendar.getInstance());
					gfHistory.setOperatedby(loginUser);

					gfHistory = giftcardHistoryService.save(gfHistory);

					//Payment detail for returned giftcard amount
					Paymentdetail newDetail = new Paymentdetail();
					newDetail.setReceived(newAmount.negate());
					newDetail.setAmount(newAmount.negate());
					newDetail.setPaymentmethod(method);
					newDetail.setPaydate(Calendar.getInstance());
					newDetail.setBackchange(backchange);
					newDetail.setStatus(completed);
					newDetail.setPayment(rtn_payment);

					details.add(newDetail);

					if(!residual.equals(BigDecimal.ZERO)) {
						detail.setReceived(residual.negate());
						detail.setAmount(residual.negate());
						detail.setPaymentmethod(method);
						detail.setPaydate(Calendar.getInstance());
						detail.setBackchange(backchange);
						detail.setStatus(completed);
						detail.setPayment(rtn_payment);

						details.add(detail);
					}

				} else {

					detail.setReceived(amount);
					detail.setAmount(amount);
					detail.setPaymentmethod(method);
					detail.setPaydate(Calendar.getInstance());
					detail.setBackchange(backchange);
					detail.setStatus(completed);
					detail.setPayment(rtn_payment);

					details.add(detail);

				}
//System.out.println("amount after tax: "+amount.doubleValue());

				rtn_payment.setPaymentdetails(details);
				rtn_payment = paymentService.save(rtn_payment);

				//create new invoice for the return
				rtn_invoice.setAmount(amount);
				rtn_invoice.setOrder(order);
				rtn_invoice.setCreatedby(loginUser);
				rtn_invoice.setCreationdate(Calendar.getInstance());
				rtn_invoice = invoiceService.save(rtn_invoice);

				String mask = "000000000";
				String invoicenum = mask.substring(0, mask.length()-String.valueOf(invoice.getInvoiceid()).length())+rtn_invoice.getInvoiceid();

				rtn_invoice.setInvoicenum(invoicenum);

				//String uuid = UUID.randomUUID().toString().substring(0,6);
				Random rand = new Random();
				String rtn_barcode = rand.nextInt(9999999) + invoicenum;

				rtn_invoice.setBarcode(rtn_barcode);

				rtn_invoice = invoiceService.save(rtn_invoice);

				//create new transaction for the return
				rtn_trx.setType(type);
				rtn_trx.setAccount(trx.getAccount());
				rtn_trx.setPayment(rtn_payment);
				rtn_trx.setInvoice(rtn_invoice);
				rtn_trx.setRecordedby(loginUser);
				rtn_trx.setRecorddate(Calendar.getInstance());

				transactionService.save(rtn_trx);

				Object mutex = WebUtils.getSessionMutex(httpSession);

				synchronized(mutex) {
					httpSession.setAttribute("invoiceList", invoiceList);
					httpSession.setAttribute("invoice",rtn_invoice);
					model.addAttribute("rtn_invoice", rtn_invoice);
					//httpSession.setAttribute("",);
				}

			}

		}

		model.addAttribute("barcode", barcode);
		model.addAttribute("invoice", invoice);
		model.addAttribute("orderitems", oitmDTOs);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Sales");

		return rtn;
	}


	@GetMapping(value="/salesummary")
	public String dspSaleSummary(ModelMap model, HttpSession httpSession) {

		logger.info("dspSaleSummary is called");
		String rtn = "/store/admin/salesummary";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		List<Transaction> txs = transactionService.getAllBySore(store); //getAllByDate();

		model.addAttribute("txs", txs);
		model.addAttribute("loginUser", loginUser);

		return rtn;
	}


	@GetMapping(value="/salesummary/printinvoice/{invoiceid}")
	public String doReprint(@PathVariable("invoiceid") Long invoiceid, ModelMap model, HttpSession httpSession) {

		logger.info("doReprint is called");
		String rtn = "store/admin/invoice";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		Invoice invoice = invoiceService.getById(invoiceid);
		Order order = invoice.getOrder();

		List<Transaction> txs = transactionService.getByInvoice(invoice);
		List<Paymentdetail> payments = new ArrayList<>();
		String txType = "";

		List<Inventory> saleList = new ArrayList<>();
		List<Orderitem> oitms = order.getOrderitems();

		for(Orderitem oitm : oitms) {
			saleList.add(inventoryService.getByItemid(oitm.getItem().getItemid()));
		}

		if(txs != null && !txs.isEmpty()) {
			for(Transaction tx : txs) {
				payments.addAll(tx.getPayment().getPaymentdetails());
				txType = tx.getType().getName();
			}
		}

		BarcodeProcessor processor = new BarcodeProcessor();
		String image = processor.process(invoice.getBarcode());

		processCart(model, saleList, order);

		model.addAttribute("order", order);
		model.addAttribute("payments", payments);
		model.addAttribute("txType", txType);
		model.addAttribute("invoice", invoice);
		model.addAttribute("saleList", saleList);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("barcode", image);

		return rtn;
	}


	@RequestMapping(method=RequestMethod.GET, value="/giftcardbalance")
	public String dspGiftcard(ModelMap model, HttpSession httpSession) {
		String rtn = "/store/admin/giftcardbalance";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Sales");

		return rtn;
	}


	@RequestMapping(method=RequestMethod.POST, value="/giftcardbalance")
	public String doGiftcard(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "/store/admin/giftcardbalance";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

		model.addAttribute("store", store);

		String cardnum = requestParams.get("cardnum");
		String pin = requestParams.get("pin");

		Giftcard giftcard = null;

		if(cardnum != null && !cardnum.isEmpty()) {

			if(pin != null && !pin.isEmpty()) {
				giftcard = giftcardService.getByCodePin(cardnum, pin);
			} else {
				giftcard = giftcardService.getByCode(cardnum);
			}

		}

		model.addAttribute("giftcard", giftcard);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Sales");

		return rtn;
	}


	@RequestMapping(method=RequestMethod.POST, value="/transferbalance")
	public String doGiftcardTransfer(@RequestParam Map<String,String> requestParams, ModelMap model, HttpSession httpSession) {
		String rtn = "/store/admin/giftcardbalance";

		Object principal = getPrincipal();

		User loginUser = getLoginUser(principal);
		Staff staff = staffService.getByUser(loginUser);

		if(!isStoreAdmin(principal) || staff == null) {
			logger.error("The login user {} is not a store admin.", loginUser.getUserid());
			return "redirect:/store/admin/logout";
		}

		Store store = staff.getStore();

System.out.println("Giftcard transferbalance");

		String cardnum0 = requestParams.get("cardnum0");
		String cardnum1 = requestParams.get("cardnum1");
		String cardnum2 = requestParams.get("cardnum2");
		String cardnum3 = requestParams.get("cardnum3");
		String transfer1 = requestParams.get("transfer1");
		String transfer2 = requestParams.get("transfer2");
		String transfer3 = requestParams.get("transfer3");
		String msg = "";

		Giftcard giftcard0 = giftcardService.getByCode(cardnum0);

		BigDecimal balance = giftcard0.getBalance();

		if(cardnum1 != null && transfer1 != null && !transfer1.isEmpty()) {
			Giftcard giftcard1 = giftcardService.getByCode(cardnum1);

			if(giftcard1 != null) {
				BigDecimal balance1 = giftcard1.getBalance();
				BigDecimal amount1 = new BigDecimal(transfer1);
				if(balance1.compareTo(amount1) >= 0) {
					giftcard1.setBalance(balance1.subtract(amount1));
					balance = balance.add(amount1);
					System.out.println("Giftcard balance1: "+balance);
					giftcard0.setBalance(balance);
					giftcard1 = giftcardService.save(giftcard1);
					giftcard0 = giftcardService.save(giftcard0);

					GiftcardHistory gfHistory0 = new GiftcardHistory();
					GiftcardHistory gfHistory1 = new GiftcardHistory();
					//History
					Type gfType0 = typeService.getByNameReftbl("Receive", "giftcards");
					Type gfType1 = typeService.getByNameReftbl("Transfer", "giftcards");
					gfHistory0.setType(gfType0);
					gfHistory0.setGiftcard(giftcard0);
					gfHistory0.setAmount(amount1);
					gfHistory0.setRefcard(giftcard1);
					gfHistory0.setOperatedate(Calendar.getInstance());
					gfHistory0.setOperatedby(loginUser);
					gfHistory0.setBalance(balance);
					gfHistory0 = giftcardHistoryService.save(gfHistory0);

					gfHistory1.setType(gfType1);
					gfHistory1.setGiftcard(giftcard1);
					gfHistory1.setAmount(amount1);
					gfHistory1.setRefcard(giftcard0);
					gfHistory1.setOperatedate(Calendar.getInstance());
					gfHistory1.setOperatedby(loginUser);
					gfHistory1.setBalance(balance1.subtract(amount1));
					gfHistory1 = giftcardHistoryService.save(gfHistory1);
					msg = "From card #" + cardnum1 + " transferred amount:  CA$" + transfer1;
				}
			} else {
				logger.error("The gift card with {} can not be found.", cardnum1);
			}
		}

		if(cardnum2 != null && transfer2 != null && !transfer2.isEmpty()) {
			Giftcard giftcard2 = giftcardService.getByCode(cardnum2);

			if(giftcard2 != null) {
				BigDecimal balance2 = giftcard2.getBalance();
				BigDecimal amount2 = new BigDecimal(transfer2);
				if(balance2.compareTo(amount2) >= 0) {
					giftcard2.setBalance(balance2.subtract(amount2));
					balance = balance.add(amount2);
					System.out.println("Giftcard balance2: "+balance);
					giftcard0.setBalance(balance);
					giftcardService.save(giftcard2);
					giftcard0 = giftcardService.save(giftcard0);
					GiftcardHistory gfHistory0 = new GiftcardHistory();
					GiftcardHistory gfHistory2 = new GiftcardHistory();
					//History
					Type gfType0 = typeService.getByNameReftbl("Receive", "giftcards");
					Type gfType1 = typeService.getByNameReftbl("Transfer", "giftcards");
					gfHistory0.setType(gfType0);
					gfHistory0.setGiftcard(giftcard0);
					gfHistory0.setAmount(amount2);
					gfHistory0.setRefcard(giftcard2);
					gfHistory0.setOperatedate(Calendar.getInstance());
					gfHistory0.setOperatedby(loginUser);
					gfHistory0.setBalance(balance);
					gfHistory0 = giftcardHistoryService.save(gfHistory0);

					gfHistory2.setType(gfType1);
					gfHistory2.setGiftcard(giftcard2);
					gfHistory2.setAmount(amount2);
					gfHistory2.setRefcard(giftcard0);
					gfHistory2.setOperatedate(Calendar.getInstance());
					gfHistory2.setOperatedby(loginUser);
					gfHistory2.setBalance(balance2.subtract(amount2));
					gfHistory2 = giftcardHistoryService.save(gfHistory2);
					msg += "<br/> From card #" + cardnum2 + " transferred amount:  CA$" + transfer2;
				}
			} else {
				logger.error("The gift card with {} can not be found.", cardnum1);
			}
		}

		if(transfer3 != null && !transfer3.isEmpty()) {
			Giftcard giftcard3 = giftcardService.getByCode(cardnum3);

			if(giftcard3 != null) {
				BigDecimal balance3 = giftcard3.getBalance();
				BigDecimal amount3 = new BigDecimal(transfer3);
				if(balance3.compareTo(amount3) >= 0) {
					giftcard3.setBalance(balance3.subtract(amount3));
					balance = balance.add(amount3);
					System.out.println("Giftcard balance3: "+balance);
					giftcard0.setBalance(balance);
					giftcardService.save(giftcard3);
					giftcardService.save(giftcard0);

					//History
					GiftcardHistory gfHistory0 = new GiftcardHistory();
					GiftcardHistory gfHistory3 = new GiftcardHistory();

					Type gfType0 = typeService.getByNameReftbl("Receive", "giftcards");
					Type gfType1 = typeService.getByNameReftbl("Transfer", "giftcards");
					gfHistory0.setType(gfType0);
					gfHistory0.setGiftcard(giftcard0);
					gfHistory0.setAmount(amount3);
					gfHistory0.setRefcard(giftcard3);
					gfHistory0.setOperatedate(Calendar.getInstance());
					gfHistory0.setOperatedby(loginUser);
					gfHistory0.setBalance(balance);
					gfHistory0 = giftcardHistoryService.save(gfHistory0);

					gfHistory3.setType(gfType1);
					gfHistory3.setGiftcard(giftcard3);
					gfHistory3.setAmount(amount3);
					gfHistory3.setRefcard(giftcard0);
					gfHistory3.setOperatedate(Calendar.getInstance());
					gfHistory3.setOperatedby(loginUser);
					gfHistory3.setBalance(balance3.subtract(amount3));
					gfHistory3 = giftcardHistoryService.save(gfHistory3);
					msg += "<br/> From card #" + cardnum3 + " transferred amount:  CA$" + transfer3;
				}
			} else {
				logger.error("The gift card with {} can not be found.", cardnum3);
			}
		}

		giftcard0 = giftcardService.getByCode(cardnum0);

		System.out.println("Giftcard msg: "+msg);

		model.addAttribute("giftcard", giftcard0);
		model.addAttribute("msg", msg);
		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Sales");

		return rtn;
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



//	private BigDecimal calTotal(List<Inventory> inv) {
//		BigDecimal total = BigDecimal.ZERO;
//
//		for(Inventory i: inv) {
//			total = total.add(i.getItem().getProduct().getFinalprice());
//		}
//		return total;
//	}



	private BigDecimal convertAmount(String amount) {
		if(amount == null || amount.isEmpty()) {
			amount = "0";
		}
		BigDecimal amt = new BigDecimal(amount);
		amt.setScale(2, BigDecimal.ROUND_HALF_UP);
		return amt;
	}



	private void processCart(ModelMap model, List<Inventory> inventories, Order order) {

		BigDecimal total = BigDecimal.ZERO;
		BigDecimal tax = BigDecimal.ZERO;
		BigDecimal subTotal = BigDecimal.ZERO;

		if(inventories != null && !inventories.isEmpty()) {
			for(Inventory i: inventories) {
				//total = total.add(i.getItem().getProduct().getFinalprice());
				Product p = i.getItem().getProduct();
				BigDecimal tmpTotal = p.getFinalprice();
				BigDecimal tmpTax = BigDecimal.ZERO;

				if(tmpTotal != null) {
					logger.info("subTotal: " + tmpTotal.toPlainString());

					/*if(p.getSize().getType().getName().equals("Kids")) {
						tmpTax = tmpTotal.multiply(new BigDecimal("0.05"));
					} else {
						tmpTax = tmpTotal.multiply(new BigDecimal("0.13"));
					}*/
System.out.println("Product ID: "+p.getProductid()+", p.getTax(): "+p.getTax());
					tmpTax = tmpTotal.multiply(new BigDecimal(p.getTax())).divide(new BigDecimal("100"));

					subTotal = subTotal.add(tmpTotal);
					tax = tax.add(tmpTax);
					tax = tax.setScale(2, BigDecimal.ROUND_HALF_UP);
					total = total.add(tmpTotal);
					total = total.add(tmpTax);
					total = total.setScale(2, BigDecimal.ROUND_HALF_UP);
				}
			}
			BigDecimal temp = total;
			if(order != null && order.getDiscount() != null) {
				total = temp.subtract(order.getDiscount());
				total = total.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			if(order != null && order.getPromocode() != null) {
				total = total.subtract(temp.multiply(new BigDecimal(order.getPromocode().getPercentage())).divide(new BigDecimal(100)));
				total = total.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
		}

		//msg.setStatus(Message.SUCCESS);
		//msg.setMessage("No item being found, please try a different UPC");

		model.addAttribute("subTotal", subTotal);
		model.addAttribute("tax", tax);
		model.addAttribute("total", total);
	}
}
