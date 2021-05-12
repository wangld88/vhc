package com.vhc.dto;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import com.vhc.core.model.Account;
import com.vhc.core.model.Customer;
import com.vhc.core.model.Invoice;
import com.vhc.core.model.Order;
import com.vhc.core.model.Orderitem;
import com.vhc.core.model.Payment;
import com.vhc.core.model.Paymentdetail;
import com.vhc.core.model.Promocode;
import com.vhc.core.model.Staff;
import com.vhc.core.model.Status;
import com.vhc.core.model.Store;
import com.vhc.core.model.Transaction;
import com.vhc.core.model.Type;
import com.vhc.core.model.User;

public class TransactionDTO {

	private long transactionid;
	private long accountid;
	private long invoiceid;
	private long orderid;
	private String invoicenum;
	private BigDecimal amount;
	private Order order;
	private String barcode;
	private String actstatusname;
	private Status actstatus;
	private Account account;
	private Invoice invoice;
	private Store store;
	private Staff staff;
	private Payment payment;
	private Type type;
	private User recordedby;
	private Calendar recorddate;
	private List<Orderitem> orderitems;
	private String shippingmethod;
	private BigDecimal discount;
	private Promocode promocode;
	private Status orderstatus;
	private String orderstatusname;
	private List<Paymentdetail> paymentdetails;
	private String customeremail;
	private String customerfname;
	private String customerlname;
	private String customername;

	public TransactionDTO() {

	}

	public TransactionDTO(Transaction trx) {
		this.transactionid = trx.getTransactionid();
		this.accountid = trx.getAccount().getAccountid();
		this.actstatusname = trx.getAccount().getStatus().getName();
		Invoice invoice = trx.getInvoice();
		this.invoiceid = invoice.getInvoiceid();
		this.invoicenum = invoice.getInvoicenum();
		this.amount = invoice.getAmount();
		this.barcode = invoice.getBarcode();
		Order order = invoice.getOrder();
		this.orderstatusname = order.getStatus().getName();
		this.orderitems = order.getOrderitems();
		this.shippingmethod = order.getShippingmethod().getName();
		this.discount = order.getDiscount();
		this.promocode = order.getPromocode();
		this.payment = trx.getPayment();
		this.paymentdetails = payment.getPaymentdetails();
		this.type = trx.getType();
		this.recorddate = trx.getRecorddate();
		Customer c = trx.getAccount().getCustomer();
		this.customeremail = c.getUser().getEmail();
		this.customerfname = c.getUser().getFirstname();
		this.customerlname = c.getUser().getLastname();
		this.customername = customerfname + " " + customerlname;

	}

	public long getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(long transactionid) {
		this.transactionid = transactionid;
	}

	public long getAccountid() {
		return accountid;
	}

	public void setAccountid(long accountid) {
		this.accountid = accountid;
	}

	public long getInvoiceid() {
		return invoiceid;
	}

	public void setInvoiceid(long invoiceid) {
		this.invoiceid = invoiceid;
	}

	public String getInvoicenum() {
		return invoicenum;
	}

	public void setInvoicenum(String invoicenum) {
		this.invoicenum = invoicenum;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getActstatusname() {
		return actstatusname;
	}

	public void setActstatusname(String actstatusname) {
		this.actstatusname = actstatusname;
	}

	public Status getActstatus() {
		return actstatus;
	}

	public void setActstatus(Status actstatus) {
		this.actstatus = actstatus;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public User getRecordedby() {
		return recordedby;
	}

	public void setRecordedby(User recordedby) {
		this.recordedby = recordedby;
	}

	public Calendar getRecorddate() {
		return recorddate;
	}

	public void setRecorddate(Calendar recorddate) {
		this.recorddate = recorddate;
	}

	public long getOrderid() {
		return orderid;
	}

	public void setOrderid(long orderid) {
		this.orderid = orderid;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public List<Orderitem> getOrderitems() {
		return orderitems;
	}

	public void setOrderitems(List<Orderitem> orderitems) {
		this.orderitems = orderitems;
	}

	public String getShippingmethod() {
		return shippingmethod;
	}

	public void setShippingmethod(String shippingmethod) {
		this.shippingmethod = shippingmethod;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public Promocode getPromocode() {
		return promocode;
	}

	public void setPromocode(Promocode promocode) {
		this.promocode = promocode;
	}

	public Status getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(Status orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getOrderstatusname() {
		return orderstatusname;
	}

	public void setOrderstatusname(String orderstatusname) {
		this.orderstatusname = orderstatusname;
	}

	public List<Paymentdetail> getPaymentdetails() {
		return paymentdetails;
	}

	public void setPaymentdetails(List<Paymentdetail> paymentdetails) {
		this.paymentdetails = paymentdetails;
	}

	public String getCustomeremail() {
		return customeremail;
	}

	public void setCustomeremail(String customeremail) {
		this.customeremail = customeremail;
	}

	public String getCustomerfname() {
		return customerfname;
	}

	public void setCustomerfname(String customerfname) {
		this.customerfname = customerfname;
	}

	public String getCustomerlname() {
		return customerlname;
	}

	public void setCustomerlname(String customerlname) {
		this.customerlname = customerlname;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

}
