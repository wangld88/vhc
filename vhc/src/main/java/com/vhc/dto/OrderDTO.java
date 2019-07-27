package com.vhc.dto;

import java.math.BigDecimal;
import java.util.List;

import com.vhc.core.model.Customer;
import com.vhc.core.model.Order;
import com.vhc.core.model.Orderitem;
import com.vhc.core.model.Staff;
import com.vhc.core.model.Store;

public class OrderDTO {

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

	private long orderid;

	private Customer customer;

	private Store store;

	private Staff staff;

	private BigDecimal amount;

	private List<Orderitem> orderitems;


	public OrderDTO() {

	}

	public OrderDTO(Order order) {

		customer = order.getCustomer();
		store = order.getStore();
		staff = order.getStaff();
		amount = order.getAmount();
		orderitems = order.getOrderitems();

	}

}
