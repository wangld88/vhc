package com.vhc.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.vhc.core.model.Inventory;
import com.vhc.core.model.Item;
import com.vhc.core.model.Product;
import com.vhc.core.model.Purchaseorder;
import com.vhc.core.model.Shipment;
import com.vhc.core.model.Size;
import com.vhc.core.model.User;

public class ItemForm {

	private long itemid;
	private String sku;
	private double cost;
	private double price;
	private long quantity;
	private User receivedby;
	private Calendar receivedate;
	private User sentby;
	private Calendar senddate;
	private String comments;
	private Product product;
	private Size size;
	private Shipment shipment;
	private Purchaseorder purchaseorder;
	private List<Inventory> inventories = new ArrayList<>();


	public ItemForm() {

	}


	public ItemForm(Item item) {

		this.itemid = item.getItemid();
		this.sku = item.getSku();
		this.cost = item.getCost();
		this.price = item.getPrice();
		this.quantity = item.getQuantity();
		this.receivedby = item.getReceivedby();
		this.receivedate = item.getReceivedate();
		this.sentby = item.getSentby();
		this.senddate = item.getSenddate();
		this.comments = item.getComments();
		this.product = item.getProduct();
		this.size = item.getSize();
		this.shipment = item.getShipment();
		this.purchaseorder = item.getPurchaseorder();
		this.inventories = item.getInventories();

	}

	public Item getItem() {
		Item item = new Item();
		item.setItemid(itemid);
		item.setSku(sku);
		item.setCost(cost);
		item.setPrice(price);
		item.setQuantity(quantity);
		item.setReceivedby(receivedby);
		item.setReceivedate(receivedate);
		item.setSentby(sentby);
		item.setSenddate(senddate);
		item.setComments(comments);
		item.setProduct(product);
		item.setSize(size);
		item.setShipment(shipment);
		item.setPurchaseorder(purchaseorder);
		item.setInventories(inventories);

		return item;
	}


	public long getItemid() {
		return itemid;
	}


	public void setItemid(long itemid) {
		this.itemid = itemid;
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


	public long getQuantity() {
		return quantity;
	}


	public void setQuantity(long quantity) {
		this.quantity = quantity;
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


	public com.vhc.core.model.Size getSize() {
		return size;
	}


	public void setSize(com.vhc.core.model.Size size) {
		this.size = size;
	}


	public Shipment getShipment() {
		return shipment;
	}


	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
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

}
