package com.vhc.dto;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import com.vhc.core.model.Customer;
import com.vhc.core.model.Invoice;
import com.vhc.core.model.Order;
import com.vhc.core.model.Orderitem;
import com.vhc.core.model.Promocode;
import com.vhc.core.model.Staff;
import com.vhc.core.model.Store;

public class OrderDTO {

	private long orderid;

	private Customer customer;

	private Store store;

	private Staff staff;

	private BigDecimal amount;

	private List<Orderitem> orderitems;

	private List<Invoice> invoices;

	private BigDecimal discount;

	private Promocode promocode;

	private Calendar creationdate;

	public OrderDTO() {

	}

	public OrderDTO(Order order) {

		this.orderid = order.getOrderid();
		this.customer = order.getCustomer();
		this.store = order.getStore();
		this.staff = order.getStaff();
		this.amount = order.getAmount();
		this.orderitems = order.getOrderitems();
		this.invoices = order.getInvoices();
		this.discount = order.getDiscount();
		this.promocode = order.getPromocode();
		this.creationdate = order.getCreationdate();
	}

	public long getOrderid() {
		return orderid;
	}

	public void setOrderid(long orderid) {
		this.orderid = orderid;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public List<Orderitem> getOrderitems() {
		return orderitems;
	}

	public void setOrderitems(List<Orderitem> orderitems) {
		this.orderitems = orderitems;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
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

	public Calendar getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Calendar creationdate) {
		this.creationdate = creationdate;
	}

}
