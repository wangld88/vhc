package com.vhc.dto;

import java.util.Calendar;

import com.vhc.model.Address;
import com.vhc.model.User;

public class CustomerDTO {

	private long customerid;
	private String comments;
	private Calendar creationdate;
	private User user;
	private Address address;
	public long getCustomerid() {
		return customerid;
	}
	public void setCustomerid(long customerid) {
		this.customerid = customerid;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Calendar getCreationdate() {
		return creationdate;
	}
	public void setCreationdate(Calendar creationdate) {
		this.creationdate = creationdate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
}
