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
	
	@Column(nullable=true, length=20)
	@Size(max=20)
	@ApiObjectField(description="Unique sku", format="Not Null, maxlength = 20", required=false)
	private String sku;

	@Column(nullable=true, length=10, columnDefinition="Decimal(6,2)")
	//@Size(max=10)
	@ApiObjectField(description="cost", format="maxlength = 10", required=false)
	private double cost;

	@Column(nullable=true, length=10, columnDefinition="Decimal(6,2)")
	//@Size(max=10)
	@ApiObjectField(description="price", format="maxlength = 10", required=false)
	private double price;

	@Column(nullable=true)
	@ApiObjectField(description="Quantity", required=false)
	private long quantity;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="receivedby")
	@ApiObjectField(description="Receive User", required=true)
	private User receivedby;

	@Column(nullable=true, length=10)
	//@Size(max=10)
	@ApiObjectField(description="receivedate", format="maxlength = 10", required=false)
	private Calendar receivedate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="sentby")
	@ApiObjectField(description="Sent User", required=true)
	private User sentby;

	@Column(nullable=true, length=10)
	//@Size(max=10)
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

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="sizeid")
	@ApiObjectField(description="Product Size", required=true)
	private com.vhc.model.Size size;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="shipmentid")
	@ApiObjectField(description="Unique Shipment", required=true)
	private Shipment shipment;

	
	public Item() {
		
	}
	
	public long getItemid() {
		return itemid;
	}

	public void setItemid(long itemid) {
		this.itemid = itemid;
	}


	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
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

	public User getReceivedby() {
		return receivedby;
	}

	public void setReceivedby(User receivedby) {
		this.receivedby = receivedby;
	}

	public Calendar getReceivedate() {
		return receivedate;
	}

	public void setReceivedate(Calendar receivedate) {
		this.receivedate = receivedate;
	}

	public User getSentby() {
		return sentby;
	}

	public void setSentby(User sentby) {
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

	public Shipment getShipment() {
		return shipment;
	}

	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}
	
	public com.vhc.model.Size getSize() {
		return size;
	}

	public void setSize(com.vhc.model.Size size) {
		this.size = size;
	}

}
