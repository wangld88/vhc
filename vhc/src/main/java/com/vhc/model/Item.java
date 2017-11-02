package com.vhc.model;

import java.io.Serializable;
import java.util.Calendar;

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
@Table(name="ITEMS")
@NamedQuery(name="Item.findAll", query="SELECT i FROM Item i")
public class Item implements Serializable {

	private static final long serialVersionUID = 3774555394233319520L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "itemid", updatable = false, nullable = false)
	private long itemid;
	
	@Column(nullable=false, unique=true, length=200)
	@Size(max=200)
	@ApiObjectField(description="Item's Name", format="Not Null, maxlength = 200", required=true)
	private String name;
	
	@Column(nullable=true, unique=true, length=40)
	@Size(max=40)
	@ApiObjectField(description="Unique Code", format="Not Null, maxlength = 40", required=false)
	private String code;

	@Column(nullable=true, unique=true, length=50)
	@Size(max=50)
	@ApiObjectField(description="Unique Barcode", format="Not Null, maxlength = 50", required=false)
	private String barcode;

	@Column(nullable=true, length=20)
	@Size(max=20)
	@ApiObjectField(description="Unique sku", format="Not Null, maxlength = 20", required=false)
	private String sku;

	@Column(nullable=true, length=10)
	@Size(max=10)
	@ApiObjectField(description="size", format="maxlength = 10", required=false)
	private String size;

	@Column(nullable=true, length=20)
	@Size(max=20)
	@ApiObjectField(description="color", format="maxlength = 20", required=false)
	private String color;

	@Column(nullable=true, length=10)
	@Size(max=10)
	@ApiObjectField(description="sex", format="maxlength = 10", required=false)
	private String sex;

	@Column(nullable=true, length=10, columnDefinition="Decimal(6,2)")
	//@Size(max=10)
	@ApiObjectField(description="cost", format="maxlength = 10", required=false)
	private double cost;

	@Column(nullable=true, length=10, columnDefinition="Decimal(6,2)")
	//@Size(max=10)
	@ApiObjectField(description="price", format="maxlength = 10", required=false)
	private double price;

	@Column(nullable=true, length=10)
	@Size(max=10)
	@ApiObjectField(description="receivedby", format="maxlength = 10", required=false)
	private String receivedby;

	@Column(nullable=true, length=10)
	@Size(max=10)
	@ApiObjectField(description="receivedate", format="maxlength = 10", required=false)
	private Calendar receivedate;

	@Column(nullable=true, length=10)
	@Size(max=10)
	@ApiObjectField(description="sentby", format="maxlength = 10", required=false)
	private String sentby;

	@Column(nullable=true, length=10)
	@Size(max=10)
	@ApiObjectField(description="senddate", format="maxlength = 10", required=false)
	private Calendar senddate;

	@Column(nullable=true, length=600)
	@Size(max=600)
	@ApiObjectField(description="Web Site", format="maxlength = 600", required=false)
	private String comments;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="productid")
	@ApiObjectField(description="Unique Product", required=true)
	private Product product;

	
	public Item() {
		
	}
	
	public long getItemid() {
		return itemid;
	}

	public void setItemid(long itemid) {
		this.itemid = itemid;
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

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getReceivedby() {
		return receivedby;
	}

	public void setReceivedby(String receivedby) {
		this.receivedby = receivedby;
	}

	public Calendar getReceivedate() {
		return receivedate;
	}

	public void setReceivedate(Calendar receivedate) {
		this.receivedate = receivedate;
	}

	public String getSentby() {
		return sentby;
	}

	public void setSentby(String sentby) {
		this.sentby = sentby;
	}

	public Calendar getSenddate() {
		return senddate;
	}

	public void setSenddate(Calendar senddate) {
		this.senddate = senddate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
}
