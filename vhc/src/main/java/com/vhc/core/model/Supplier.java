package com.vhc.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.jsondoc.core.annotation.ApiObjectField;

@Entity
@Table(name="SUPPLIERS")
@NamedQuery(name="Supplier.findAll", query="SELECT s FROM Supplier s")
public class Supplier implements Serializable {

	private static final long serialVersionUID = -3040831285573374995L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "supplierid", updatable = false, nullable = false)
	private long supplierid;
	
	@Column(nullable=false, unique=true, length=200)
	@Size(max=200)
	@ApiObjectField(description="Item's Name", format="Not Null, maxlength = 200", required=true)
	private String name;
	
	@Column(nullable=true, length=50)
	@Size(max=50)
	@ApiObjectField(description="Manufacture's Contact Name", format="maxlength = 50", required=false)
	private String contact;
	
	@Column(nullable=true, length=30)
	@Size(max=30)
	@ApiObjectField(description="Phone Number", format="maxlength = 30", required=false)
	private String phone;
	
	@Column(nullable=true, length=50)
	@Size(max=50)
	@ApiObjectField(description="Email Address", format="maxlength = 50", required=false)
	private String email;
	
	@Column(nullable=true, length=100)
	@Size(max=100)
	@ApiObjectField(description="Web Site", format="maxlength = 100", required=false)
	private String website;

	@Column(nullable=true, length=600)
	@Size(max=600)
	@ApiObjectField(description="Web Site", format="maxlength = 600", required=false)
	private String comments;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="addressid")
	@ApiObjectField(description="Unique Address", format="Not Null", required=false)
	private Address address;


	public Supplier() {
		
	}

	public long getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(long supplierid) {
		this.supplierid = supplierid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
