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
@Table(name="INVENTORIES")
@NamedQuery(name="Inventory.findAll", query="SELECT i FROM Inventory i")
public class Inventory implements Serializable {

	private static final long serialVersionUID = -748565879849839762L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "inventory", updatable = false, nullable = false)
	private long inventoryid;
	
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

	
	public Inventory() {
		
	}
	
	public long getInventoryid() {
		return inventoryid;
	}

	public void setInventoryid(long inventoryid) {
		this.inventoryid = inventoryid;
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

}
