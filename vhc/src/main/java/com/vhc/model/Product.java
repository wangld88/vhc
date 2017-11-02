package com.vhc.model;

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

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;


@Entity
@Table(name="PRODUCTS")
@ApiObject
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {

	private static final long serialVersionUID = -2385234332215716043L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "productid", updatable = false, nullable = false)
	private long productid;
	
	@Column(nullable=false, unique=true, length=200)
	@Size(max=200)
	@ApiObjectField(description="Manufacture's Name", format="Not Null, maxlength = 200", required=true)
	private String name;
	
	@Column(nullable=true, unique=true, length=40)
	@Size(max=40)
	@ApiObjectField(description="Unique Name", format="Not Null, maxlength = 40", required=false)
	private String code;

	@Column(nullable=true, length=400)
	@Size(max=400)
	@ApiObjectField(description="Description", format="Not Null, maxlength = 400", required=false)
	private String description;
	        
	@Column(nullable=true, length=600)
	@Size(max=600)
	@ApiObjectField(description="Web Site", format="maxlength = 600", required=false)
	private String comments;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="brandid")
	@ApiObjectField(description="Unique Brand", required=true)
	private Brand brand;

	
	public Product() {
		
	}
	
	public long getProductid() {
		return productid;
	}

	public void setProductid(long productid) {
		this.productid = productid;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

}
