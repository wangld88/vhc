package com.vhc.dto;

import java.util.List;

import com.vhc.model.Address;
import com.vhc.model.Customer;
import com.vhc.model.Item;
import com.vhc.model.Payment;


public class ShoppingCart {

	private List<Item> items;

	private long count;

	private Customer customer;

	private Address deliveryAddress;

	private Payment payment;

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
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
