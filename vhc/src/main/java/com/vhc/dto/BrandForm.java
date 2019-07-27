package com.vhc.dto;

import java.sql.Blob;

import com.vhc.core.model.Address;
import com.vhc.core.model.Brand;
import com.vhc.util.ImageProcessor;

public class BrandForm {

	private long brandid;

	private String name;

	private String description;

	private String contact;

	private String phone;

	private String email;

	private String website;

	private String comments;

	private Address address;

	private Blob image;

	private String imageData;


	public BrandForm() {

	}

	public BrandForm(Brand brand) {
		this.brandid = brand.getBrandid();
		this.name = brand.getName();
		this.image = brand.getImage();
		this.description = brand.getDescription();
		this.contact = brand.getContact();
		this.email = brand.getEmail();
		this.phone = brand.getPhone();
		this.website = brand.getWebsite();
		this.address = brand.getAddress();
		this.comments = brand.getComments();

		ImageProcessor processor = new ImageProcessor();
		this.imageData = processor.getImageByteArray(brand.getImage());
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

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public String getImageData() {
		return imageData;
	}

	public void setImageData(String imageData) {
		this.imageData = imageData;
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

}
