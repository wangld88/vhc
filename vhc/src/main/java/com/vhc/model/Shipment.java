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
@Table(name="SHIPMENTS")
@NamedQuery(name="Shipment.findAll", query="SELECT s FROM Shipment s")
public class Shipment implements Serializable {

	private static final long serialVersionUID = -4680186460232853214L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "shipmentid", updatable = false, nullable = false)
	private long shipmentid;
	
	@Column(nullable=false, unique=true, length=200)
	@Size(max=200)
	@ApiObjectField(description="Item's Name", format="Not Null, maxlength = 200", required=true)
	private String code;
	
	@Column(nullable=true, length=10)
	@Size(max=10)
	@ApiObjectField(description="receivedby", format="maxlength = 10", required=false)
	private String receivedby;

	@Column(nullable=true, length=10)
	@Size(max=10)
	@ApiObjectField(description="receivedate", format="maxlength = 10", required=false)
	private Calendar receivedate;

	@Column(nullable=true, length=600)
	@Size(max=600)
	@ApiObjectField(description="Web Site", format="maxlength = 600", required=false)
	private String comments;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="supplierid")
	@ApiObjectField(description="Unique Supplier", required=true)
	private Supplier supplier;

	
	public Shipment() {
		
	}
	
	public long getShipmentid() {
		return shipmentid;
	}

	public void setShipmentid(long shipmentid) {
		this.shipmentid = shipmentid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

}
