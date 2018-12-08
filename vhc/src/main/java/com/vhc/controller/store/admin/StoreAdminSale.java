package com.vhc.controller.store.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.vhc.controller.store.StoreBase;
import com.vhc.model.Account;
import com.vhc.model.Creditcard;
import com.vhc.model.Customer;
import com.vhc.model.Debitcard;
import com.vhc.model.Giftcard;
import com.vhc.model.Inventory;
import com.vhc.model.Invoice;
import com.vhc.model.Item;
import com.vhc.model.Order;
import com.vhc.model.Orderitem;
import com.vhc.model.Payment;
import com.vhc.model.Paymentmethod;
import com.vhc.model.Product;
import com.vhc.model.Promocode;
import com.vhc.model.Staff;
import com.vhc.model.Status;
import com.vhc.model.Store;
import com.vhc.model.Transaction;
import com.vhc.model.Type;
import com.vhc.model.User;
import com.vhc.security.LoginUser;
import com.vhc.util.Message;
import com.vhc.util.PrintEngine;


@Controller
@RequestMapping("/store/admin")
public class StoreAdminSale extends StoreBase {

	Logger logger = LoggerFactory.getLogger(StoreAdminSale.class);


	@RequestMapping(method=RequestMethod.GET, value="/sales")
	public String dspSales(ModelMap model, @ModelAttribute("customer") Customer customer, HttpSession httpSession) {
		String rtn = "store/admin/sales";

		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);

		model.addAttribute("customer", customer);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Sales");

		return rtn;
	}


	@RequestMapping(method=RequestMethod.GET, value="/itemSale")
	public String dspItemSale(ModelMap model, @ModelAttribute("customer") Customer customer, HttpSession httpSession) {
		String rtn = "store/admin/itemsale";

		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		Status received = statusService.getByName("Received");

		Store store = staffService.getByUser(loginUser).getStore();

		List<Inventory> avaialbe = inventoryService.getByStoreAvaiable(store.getStoreid(), received);

		Object mutex = WebUtils.getSessionMutex(httpSession);

		synchronized(mutex) {
			List<Inventory> saleList = null;
			httpSession.setAttribute("saleList", saleList);
			model.addAttribute("saleList", saleList);
		}

		model.addAttribute("avaialbe", avaialbe);

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

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);
		Store store = staffService.getByUser(loginUser).getStore();
		Status received = statusService.getByName("Received");

		String sku = requestParams.get("sku");
		logger.info("sku: "+sku+", storeid: "+store.getStoreid()+", status: "+received.getStatusid());
		List<Inventory> inventories = inventoryService.getByStoreAvaiableUPC(sku, store.getStoreid(), received);

		logger.info("inventories found: " + inventories.get(0).getItem().getItemid());

		Object mutex = WebUtils.getSessionMutex(httpSession);
		BigDecimal subTotal = BigDecimal.ZERO;

		synchronized(mutex) {
			List<Inventory> saleList = null;
			if(httpSession.getAttribute("saleList") != null) {
				saleList = (List<Inventory>) httpSession.getAttribute("saleList");
				subTotal = calTotal(saleList);
			} else {
				saleList = new ArrayList<>();
			}

			if(inventories != null && !inventories.isEmpty()) {
				logger.info("inventory is found:"+inventories.size()+", sku: "+sku);
				Inventory current = inventories.get(0);
				if(isSkuNotSelected(saleList,current)) {
					saleList.add(current);
					subTotal = subTotal.add(current.getItem().getProduct().getFinalprice());
					logger.info("PRICE: "+current.getItem().getProduct().getFinalprice()+", subTotal: "+subTotal);
					httpSession.setAttribute("saleList", saleList);
				} else {
					Message msg = new Message();
					msg.setStatus(Message.ERROR);
					msg.setMessage("The item (" + sku + ") has been added already.");
					model.addAttribute("message", msg);
				}
			} else {
				Message msg = new Message();
				msg.setStatus(Message.ERROR);
				msg.setMessage("Can not find the given item in inventories.");
				model.addAttribute("message", msg);
			}
			model.addAttribute("saleList", saleList);
		}

		BigDecimal total = BigDecimal.ZERO;
		BigDecimal tax = BigDecimal.ZERO;

		if(subTotal != null) {
			logger.info("subTotal: " + subTotal.toPlainString());
			tax = subTotal;
			tax = tax.multiply(new BigDecimal("0.13"));
			tax = tax.setScale(2, BigDecimal.ROUND_HALF_UP);
			total = total.add(subTotal);
			total = total.add(tax);
			total = total.setScale(2, BigDecimal.ROUND_HALF_UP);
		}

		List<Paymentmethod> methods = paymentmethodService.getAll();

		model.addAttribute("methods", methods);
		model.addAttribute("subTotal", subTotal);
		model.addAttribute("tax",tax);
		model.addAttribute("total",total);
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

		logger.info("product sale remove is called");
		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);

		Object mutex = WebUtils.getSessionMutex(httpSession);

		synchronized(mutex) {
			if(httpSession.getAttribute("saleList") != null) {
				List<Inventory> saleList = (List<Inventory>) httpSession.getAttribute("saleList");
				List<Inventory> newList = saleList.stream().filter(inv -> inv.getInventoryid() != inventoryid).collect(Collectors.toList());
				httpSession.setAttribute("saleList", newList);
			} else {
				Message msg = new Message();
				msg.setStatus(Message.ERROR);
				msg.setMessage("There is no item to be removed.");
				model.addAttribute("message", msg);
			}

		}

		model.addAttribute("loginUser", loginUser);

		return rtn;
	}


	@SuppressWarnings("unchecked")
	@RequestMapping(method=RequestMethod.POST, value="/itemsale")
	public String doItemSale(@RequestParam Map<String,String> requestParams,
			ModelMap model,
			HttpSession httpSession) {

		String rtn = "store/admin/itemsale";

		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Sales");

		logger.info("doItemSale is called");

		Status delivered = statusService.getByName("Delivered");
		Staff staff = staffService.getByUser(loginUser);
		Store store = staff.getStore();

		List<Inventory> inventories = new ArrayList<>();

		String methodid = requestParams.get("methodid");
		String cardnum = requestParams.get("cardnum");
		String pin = requestParams.get("pin");
		String code = requestParams.get("promocode");

		Promocode p = promocodeService.getByCode(code);

		Account account = new Account();
		Payment payment = new Payment();
		Invoice invoice = new Invoice();
		Transaction trx = new Transaction();

		if(methodid != null && !methodid.isEmpty()) {

			if(!methodid.contains("1") && !methodid.contains("2") && !methodid.contains("3") && !methodid.contains("4")) {
				//invalid payment method
				Message msg = new Message();
				msg.setStatus(Message.ERROR);
				msg.setMessage("Invilad payment method.");
				model.addAttribute("message", msg);
				return "-1";
			}

			Giftcard giftcard = null;

			if(cardnum != null && !cardnum.isEmpty()) {
				if(pin != null && !pin.isEmpty()) {
					giftcard = giftcardService.getByCodePin(code, pin);

					if(giftcard != null && giftcard.getBalance().compareTo(BigDecimal.ZERO) == 0) {
						Message msg = new Message();
						msg.setStatus(Message.ERROR);
						msg.setMessage("Pin number is required.");
						model.addAttribute("message", msg);
						return "-1";
					}
				} else {
					Message msg = new Message();
					msg.setStatus(Message.ERROR);
					msg.setMessage("Pin number is required.");
					model.addAttribute("message", msg);
					return "-1";
				}


			}

			Object mutex = WebUtils.getSessionMutex(httpSession);

			synchronized(mutex) {
				List<Inventory> saleList = null;
				if(httpSession.getAttribute("saleList") != null) {
					saleList = (List<Inventory>) httpSession.getAttribute("saleList");
					if(saleList == null || saleList.isEmpty()) {
						Message msg = new Message();
						msg.setStatus(Message.ERROR);
						msg.setMessage("Please add shopping item, now the cart is empty.");
						model.addAttribute("message", msg);
						return "-1";
					}
					double total = 0.0;

					Customer customer = customerService.getById(1);

					Order order = new Order();
					order.setStore(store);
					order.setStaff(staff);
					order.setCreatedby(loginUser);
					order.setCreationdate(Calendar.getInstance());
					order.setCustomer(customer);
					order = orderService.save(order);

					Type type = typeService.getByNameReftbl("Sell", "transactions");

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
						double amt = item.getPrice();
						amt = prod.getFinalprice().doubleValue();

						if(p != null && p.getPromocodeid() != 0) {
							otm.setPromocode(p);
							amt = amt * (1 - p.getPercentage()/100);
						}

						total += amt;
						otm.setType(type);
						otm.setAmount(new BigDecimal(amt));
						orderitemService.save(otm);
					}

					BigDecimal amount = new BigDecimal(total);
					amount.setScale(2, BigDecimal.ROUND_HALF_UP);

					order.setAmount(amount);

					order = orderService.save(order);

					account = accountService.getById(1); //ByCustomer(customer)

					payment.setAmount(amount);
					payment.setCreatedby(loginUser);
					payment.setCreationdate(Calendar.getInstance());

					BigDecimal sum = BigDecimal.ZERO;
					sum.setScale(2, BigDecimal.ROUND_HALF_UP);

					//multiple payment methods
					if(methodid.contains("1")) {
						BigDecimal credit = convertAmount(requestParams.get("creditamount"));
						sum = sum.add(credit);
						payment.setCreditamount(credit);
						/*Creditcard credit = new Creditcard(cardnum, customer);
						creditcardService.save(credit);
						payment.setCreditcard(credit);*/
					}
					if(methodid.contains("2")) {
						BigDecimal debit = convertAmount(requestParams.get("debitamount"));
						sum = sum.add(debit);
						payment.setDebitamount(debit);
						/*Debitcard debit = new Debitcard(cardnum, customer);
						debitcardService.save(debit);
						payment.setDebitcard(debit);*/
					}
					if(methodid.contains("3")) {
						BigDecimal gift = convertAmount(requestParams.get("debitamount"));
						sum = sum.add(gift);
						BigDecimal balance = giftcard.getBalance().subtract(gift);
						if(balance.compareTo(BigDecimal.ZERO) < 0) {
							balance = BigDecimal.ZERO;
						}
						giftcard.setBalance(balance);
						giftcardService.save(giftcard);
						payment.setGiftamount(convertAmount(requestParams.get("giftamount")));
					}
					if (methodid.contains("4")) {
						BigDecimal cash = convertAmount(requestParams.get("cashamount"));
						sum = sum.add(cash);
						payment.setCashamount(cash);
					}

					if(amount.compareTo(sum) != 0) {
						//Error amount are different
						logger.error("Item amount: " + amount.doubleValue()+", payment amount: "+sum.doubleValue());
						Message msg = new Message();
						msg.setStatus(Message.ERROR);
						msg.setMessage("Please add shopping item, now the cart is empty.");
						model.addAttribute("message", msg);
					}

					payment = paymentService.save(payment);

					invoice.setAmount(amount);
					invoice.setOrder(order);
					invoice.setCreatedby(loginUser);
					invoice.setCreationdate(Calendar.getInstance());
					invoice = invoiceService.save(invoice);
					String invoicenum = "000000000";
					invoice.setInvoicenum(invoicenum.substring(0, invoicenum.length()-String.valueOf(invoice.getInvoiceid()).length())+invoice.getInvoiceid());
					invoice = invoiceService.save(invoice);

					trx.setType(type);
					trx.setAccount(account);
					trx.setPayment(payment);
					trx.setInvoice(invoice);
					trx.setRecordedby(loginUser);
					trx.setRecorddate(Calendar.getInstance());

					//rtn = String.valueOf(invoice.getInvoiceid());

					transactionService.save(trx);
					logger.info("The transaction completed, trx ID:" + trx.getTransactionid());
					model.addAttribute("saleList", saleList);
					httpSession.setAttribute("invoice", invoice);
					httpSession.setAttribute("invoiceList", saleList);
					httpSession.removeAttribute("saleList");
				}
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

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);

		Object mutex = WebUtils.getSessionMutex(httpSession);

		List<Inventory> saleList = null;

		synchronized(mutex) {
			if(httpSession.getAttribute("saleList") != null) {
				saleList = (List<Inventory>) httpSession.getAttribute("saleList");
			} else {
				saleList = new ArrayList<>();
			}
			httpSession.setAttribute("saleList", saleList);
			model.addAttribute("saleList", saleList);
		}

		model.addAttribute("loginUser", loginUser);

		try {
			PrintEngine printer = new PrintEngine();
			printer.setPrintData(saleList);
			printer.print();
		} catch(Exception e) {
			logger.error("Receipt printing error: "+e.getMessage());
		}
		model.addAttribute("menu", "Sales");

		return rtn;
	}

	@RequestMapping(method=RequestMethod.GET, value="/itemsale/printinvoice")
	public String dspPrintInvoice(ModelMap model, HttpSession httpSession) {

		String rtn = "store/admin/invoice";

		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);

		Object mutex = WebUtils.getSessionMutex(httpSession);

		List<Inventory> saleList = null;
		Invoice invoice = null;
		BigDecimal subTotal = BigDecimal.ZERO;

		synchronized(mutex) {

			if(httpSession.getAttribute("invoiceList") != null) {
				saleList = (List<Inventory>) httpSession.getAttribute("invoiceList");
				subTotal = calTotal(saleList);
				invoice = (Invoice) httpSession.getAttribute("invoice");
			} else {
				saleList = new ArrayList<>();
				invoice = new Invoice();
			}
			httpSession.setAttribute("saleList", saleList);
			model.addAttribute("saleList", saleList);
			model.addAttribute("invoice", invoice);
		}

		BigDecimal total = BigDecimal.ZERO;
		BigDecimal tax = BigDecimal.ZERO;

		if(subTotal != null) {
			logger.info("subTotal: " + subTotal.toPlainString());
			tax = subTotal;
			tax = tax.multiply(new BigDecimal("0.13"));
			tax = tax.setScale(2, BigDecimal.ROUND_HALF_UP);
			total = total.add(subTotal);
			total = total.add(tax);
			total = total.setScale(2, BigDecimal.ROUND_HALF_UP);
		}

		/*List<Paymentmethod> methods = paymentmethodService.getAll();

		model.addAttribute("methods", methods);*/
		model.addAttribute("subTotal", subTotal);
		model.addAttribute("tax",tax);
		model.addAttribute("total",total);
		model.addAttribute("loginUser", loginUser);

		return rtn;
	}


	@RequestMapping(method=RequestMethod.GET, value="/giftcardbalance")
	public String dspGiftcard(ModelMap model, HttpSession httpSession) {
		String rtn = "/store/admin/giftcardbalance";

		Object principal = getPrincipal();

		if(!isStoreAdmin(principal)) {
			return "redirect:/store/admin/logout";
		}

		User loginUser = getLoginUser(principal);

		model.addAttribute("loginUser", loginUser);
		model.addAttribute("menu", "Sales");

		return rtn;
	}


	private boolean isSkuNotSelected(List<Inventory> saleList, Inventory inventory) {

		List<Inventory> result = saleList.stream().filter(inv -> inv.getItem().getSku().equals(inventory.getItem().getSku())).collect(Collectors.toList());

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

	private BigDecimal calTotal(List<Inventory> inv) {
		BigDecimal total = BigDecimal.ZERO;

		for(Inventory i: inv) {
			total = total.add(i.getItem().getProduct().getFinalprice());
		}
		return total;
	}

	private BigDecimal convertAmount(String amount) {
		if(amount == null || amount.isEmpty()) {
			amount = "0";
		}
		BigDecimal amt = new BigDecimal(amount);
		amt.setScale(2, BigDecimal.ROUND_HALF_UP);
		return amt;
	}
}
