package com.vhc.core.model;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.jsondoc.core.annotation.ApiObjectField;


@Entity
@Table(name="brands")
@NamedQuery(name="Brand.findAll", query="SELECT b FROM Brand b")
public class Brand implements Serializable {

	private static final long serialVersionUID = -596658205795166084L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "brandid", updatable = false, nullable = false)
	private long brandid;

	@Column(nullable=false, length=200)
	@Size(max=200)
	@ApiObjectField(description="Brand Name", format="Not Null, maxlength = 200", required=true)
	private String name;

	@Column(nullable=true, unique=false, length=400)
	@Size(max=400)
	@ApiObjectField(description="Brand Description", format="Not Null, maxlength = 400", required=false)
	private String description;

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
	@ApiObjectField(description="Comment notes", format="maxlength = 600", required=false)
	private String comments;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="addressid")
	@ApiObjectField(description="Unique Address", format="Not Null", required=false)
	private Address address;

	@Column(nullable=true)
	@Lob
	private Blob image;

	@Column(nullable=true, length=1)
	@Size(max=1)
	@ApiObjectField(description="Flag of website display", format="maxlength = 1", required=false)
	private String display;


	public Brand() {

	}

	public long getBrandid() {
		return brandid;
	}

	public void setBrandid(long brandid) {
		this.brandid = brandid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

}