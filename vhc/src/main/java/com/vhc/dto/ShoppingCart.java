package com.vhc.dto;

import java.io.Serializable;
import java.util.List;

import com.vhc.core.model.Address;
import com.vhc.core.model.Customer;
import com.vhc.core.model.Item;
import com.vhc.core.model.Payment;


public class ShoppingCart implements Serializable {

	private static final long serialVersionUID = 5624308165287664871L;

	private List<ShopItem> items;

	private long count;

	private Customer customer;

	private Address deliveryAddress;

	private Payment payment;

	public List<ShopItem> getItems() {
		return items;
	}

	public void setItems(List<ShopItem> items) {
		this.items = items;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Address getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

}
