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
@Table(name="STORES")
@NamedQuery(name="Store.findAll", query="SELECT s FROM Store s")
public class Store implements Serializable {

	private static final long serialVersionUID = -59147124950351619L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "storeid", updatable = false, nullable = false)
	private long storeid;

	@Column(nullable=false, unique=true, length=200)
	@Size(max=200)
	@ApiObjectField(description="Store's Name", format="Not Null, maxlength = 200", required=true)
	private String name;

	@Column(nullable=false, unique=true, length=20)
	@Size(max=20)
	@ApiObjectField(description="Store's Name", format="Not Null, maxlength = 20", required=true)
	private String code;

	@Column(nullable=true, length=50)
	@Size(max=50)
	@ApiObjectField(description="Store's Contact Name", format="maxlength = 50", required=false)
	private String contact;

	@Column(nullable=true, length=30)
	@Size(max=30)
	@ApiObjectField(description="Phone Number", format="maxlength = 30", required=false)
	private String phone;

	@Column(nullable=true, length=30)
	@Size(max=30)
	@ApiObjectField(description="Mobile Number", format="maxlength = 30", required=false)
	private String mobile;

	@Column(nullable=true, length=50)
	@Size(max=50)
	@ApiObjectField(description="Email Address", format="maxlength = 50", required=false)
	private String email;

	@Column(nullable=true, length=100)
	@Size(max=100)
	@ApiObjectField(description="Web Site", format="maxlength = 100", required=false)
	private String website;

	@Column(nullable=true, length=100)
	@Size(max=100)
	@ApiObjectField(description="Facebook", format="maxlength = 100", required=false)
	private String facebook;

	@Column(nullable=true, length=100)
	@Size(max=100)
	@ApiObjectField(description="Facebook", format="maxlength = 100", required=false)
	private String google;

	@Column(nullable=true, length=100)
	@Size(max=100)
	@ApiObjectField(description="Facebook", format="maxlength = 100", required=false)
	private String twitter;

	@Column(nullable=true, length=600)
	@Size(max=600)
	@ApiObjectField(description="Web Site", format="maxlength = 600", required=false)
	private String comments;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="addressid")
	@ApiObjectField(description="Unique Address", format="Not Null", required=false)
	private Address address;


	public Store() {

	}

	public long getStoreid() {
		return storeid;
	}

	public void setStoreid(long storeid) {
		this.storeid = storeid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getGoogle() {
		return google;
	}

	public void setGoogle(String google) {
		this.google = google;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
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
