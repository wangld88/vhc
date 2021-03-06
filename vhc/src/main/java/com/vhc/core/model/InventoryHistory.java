package com.vhc.core.model;

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
@Table(name="inventoryHistories")
@NamedQuery(name="InventoryHistory.findAll", query="SELECT i FROM InventoryHistory i")
public class InventoryHistory implements Serializable {

	private static final long serialVersionUID = -748565879849839762L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "historyid", updatable = false, nullable = false)
	private long historyid;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "inventoryid")
	@ApiObjectField(description="Inventory ID", required=true)
	private Inventory inventory;

	@Column(nullable=true)
	@ApiObjectField(description="Quantity", required=false)
	private long quantity;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="receivedby")
	@ApiObjectField(description="Received by User", required=true)
	private User receivedby;

	@Column(nullable=true)
	@ApiObjectField(description="Receiving date", required=false)
	private Calendar receivedate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="sentby")
	@ApiObjectField(description="Sent by User", required=true)
	private User sentby;

	@Column(nullable=true)
	@ApiObjectField(description="Sending date", required=false)
	private Calendar senddate;

	@Column(nullable=true, length=600)
	@Size(max=600)
	@ApiObjectField(description="Comment notes", format="maxlength = 600", required=false)
	private String comments;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="storeid")
	@ApiObjectField(description="Unique Store", required=true)
	private Store store;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="itemid")
	@ApiObjectField(description="Unique Item", required=true)
	private Item item;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="statusid")
	@ApiObjectField(description="Unique Status", required=true)
	private Status status;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="locationid")
	@ApiObjectField(description="Unique Location", required=true)
	private Location location;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="deststoreid")
	@ApiObjectField(description="Unique Store", required=false)
	private Store deststore;


	public InventoryHistory() {

	}

	public InventoryHistory(Inventory inventory) {
		this.inventory = inventory;
		this.item = inventory.getItem();
		this.store = inventory.getStore();
		this.location = inventory.getLocation();
		this.status = inventory.getStatus();
		this.quantity = inventory.getQuantity();
		this.sentby = inventory.getSentby();
		this.senddate = inventory.getSenddate();
		this.receivedby = inventory.getReceivedby();
		this.receivedate = inventory.getReceivedate();
		this.comments = inventory.getComments();
		this.deststore = inventory.getDeststore();
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
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

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public long getHistoryid() {
		return historyid;
	}

	public void setHistoryid(long historyid) {
		this.historyid = historyid;
	}

	public Store getDeststore() {
		return deststore;
	}

	public void setDeststore(Store deststore) {
		this.deststore = deststore;
	}

}
