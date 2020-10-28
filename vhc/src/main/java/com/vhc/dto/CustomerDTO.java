package com.vhc.dto;

import java.util.Calendar;

import com.vhc.core.model.Address;
import com.vhc.core.model.Customer;
import com.vhc.core.model.User;

public class CustomerDTO {

	private long customerid;
	private String comments;
	private Calendar creationdate;
	private long userid;
	private String username;
	private String firstname;
	private String lastname;
	private String fullname;
	private String phone;
	private String cell;
	private String email;
	private long addressid;
	private String street;
	private String city;
	private String province;
	private String postalcode;


	public CustomerDTO() {

	}

	public CustomerDTO(Customer customer) {
		this.customerid = customer.getCustomerid();
		this.comments = customer.getComments();
		this.creationdate = customer.getCreationdate();
		if(customer.getUser() != null) {
			User user = customer.getUser();
			this.userid = user.getUserid();
			this.firstname = user.getFirstname();
			this.lastname = user.getLastname();
			this.phone = user.getPhone();
			this.cell = user.getCell();
			this.email = user.getEmail();
			this.username = user.getUsername();
		}
		if(customer.getAddress() != null) {
			Address address = customer.getAddress();
			this.addressid = address.getAddressid();
			this.street = address.getStreet();
			this.postalcode = address.getPostalcode();
			if(address.getCity() != null) {
				this.city = address.getCity().getName();
				this.province = address.getCity().getProvince().getName();
			}
		}
	}

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
	public String getUsername() {
		return username;
	}
	public void setUser(String username) {
		this.username = username;
	}
	public long getAddressid() {
		return addressid;
	}
	public void setAddress(long addressid) {
		this.addressid = addressid;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFullname() {
		this.fullname = this.firstname + " " + this.lastname;
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setAddressid(long addressid) {
		this.addressid = addressid;
	}
}
