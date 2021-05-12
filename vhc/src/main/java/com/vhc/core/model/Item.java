package com.vhc.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name="items")
@NamedQuery(name="Item.findAll", query="SELECT i FROM Item i")
public class Item implements Serializable {

	private static final long serialVersionUID = 3774555394233319520L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "itemid", updatable = false, nullable = false)
	private Long itemid;

	@Column(nullable=true, length=20)
	@Size(max=20)
	@ApiObjectField(description="Unique sku", format="Not Null, maxlength = 20", required=false)
	private String sku;

	@Column(nullable=true, length=10, columnDefinition="Decimal(6,2)")
	@ApiObjectField(description="cost", format="maxlength = 10", required=false)
	private double cost;

	@Column(nullable=true, length=10, columnDefinition="Decimal(6,2)")
	@ApiObjectField(description="price", format="maxlength = 10", required=false)
	private double price;

	@Column(nullable=true)
	@ApiObjectField(description="Quantity", required=false)
	private long quantity;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="receivedby")
	@ApiObjectField(description="Received by User", required=true)
	@JsonManagedReference
	private User receivedby;

	@Column(nullable=true, length=10)
	@ApiObjectField(description="receivedate", format="maxlength = 10", required=false)
	private Calendar receivedate;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="sentby")
	@ApiObjectField(description="Sent by User", required=true)
	@JsonManagedReference
	private User sentby;

	@Column(nullable=true, length=10)
	@ApiObjectField(description="senddate", format="maxlength = 10", required=false)
	private Calendar senddate;

	@Column(nullable=true, length=600)
	@Size(max=600)
	@ApiObjectField(description="Web Site", format="maxlength = 600", required=false)
	private String comments;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="productid")
	@ApiObjectField(description="Unique Product", required=true)
	@JsonManagedReference
	private Product product;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="sizeid")
	@ApiObjectField(description="Product Size", required=true)
	@JsonManagedReference
	private com.vhc.core.model.Size size;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="shipmentid")
	@ApiObjectField(description="Unique Shipment", required=true)
	@JsonManagedReference
	private Shipment shipment;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="poid")
	@ApiObjectField(description="Unique Purchase Order", required=true)
	@JsonManagedReference
	private Purchaseorder purchaseorder;

	@JsonIgnore
	@OneToMany(cascade={javax.persistence.CascadeType.ALL}, mappedBy="item", fetch=FetchType.LAZY)
	private List<Inventory> inventories = new ArrayList<>();

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="createdby")
	@ApiObjectField(description="Created by User", required=true)
	@JsonManagedReference
	private User createdby;

	@Column(nullable=true, length=10)
	@ApiObjectField(description="creationdate", format="maxlength = 10", required=false)
	private Calendar creationdate;

	/*@OneToOne(targetEntity = Orderitem.class, fetch=FetchType.EAGER)
	@JoinColumn(nullable = false, name="orderitemid")
	private Orderitem orderitem;*/


	public Item() {
	}

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
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

	public com.vhc.core.model.Size getSize() {
		return size;
	}

	public void setSize(com.vhc.core.model.Size size) {
		this.size = size;
	}

	public Purchaseorder getPurchaseorder() {
		return purchaseorder;
	}

	public void setPurchaseorder(Purchaseorder purchaseorder) {
		this.purchaseorder = purchaseorder;
	}

	public List<Inventory> getInventories() {
		return inventories;
	}

	public void setInventories(List<Inventory> inventories) {
		this.inventories = inventories;
	}

	public User getCreatedby() {
		return createdby;
	}

	public void setCreatedby(User createdby) {
		this.createdby = createdby;
	}

	public Calendar getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Calendar creationdate) {
		this.creationdate = creationdate;
	}

	/*public Orderitem getOrderitem() {
		return orderitem;
	}

	public void setOrder(Orderitem orderitem) {
		this.orderitem = orderitem;
	}*/

}
