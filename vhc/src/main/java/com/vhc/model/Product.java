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
	
	@Column(nullable=true, length=40)
	@Size(max=40)
	@ApiObjectField(description="Model Number", format="maxlength = 40", required=false)
	private String modelnum;

	@Column(nullable=true, length=50)
	@Size(max=50)
	@ApiObjectField(description="Universal Product Code", format="maxlength = 50", required=false)
	private String upc;

	@Column(nullable=true, length=400)
	@Size(max=400)
	@ApiObjectField(description="Description", format="maxlength = 400", required=false)
	private String description;
	        
	@Column(nullable=true, length=80)
	@Size(max=80)
	@ApiObjectField(description="Style", format="maxlength = 80", required=false)
	private String style;
	        
	@Column(nullable=true, length=80)
	@Size(max=80)
	@ApiObjectField(description="Material", format="maxlength = 80", required=false)
	private String material;
	        
	@Column(nullable=true, length=600)
	@Size(max=600)
	@ApiObjectField(description="Commnents", format="maxlength = 600", required=false)
	private String comments;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="brandid")
	@ApiObjectField(description="Unique Brand", required=true)
	private Brand brand;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="typeid")
	@ApiObjectField(description="Product Type", required=true)
	private Type type;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="colorid")
	@ApiObjectField(description="Product Color", required=true)
	private Color color;

	
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

	public String getModelnum() {
		return modelnum;
	}

	public void setModelnum(String modelnum) {
		this.modelnum = modelnum;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
