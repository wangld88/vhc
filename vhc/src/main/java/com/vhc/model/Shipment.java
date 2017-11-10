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
	@ApiObjectField(description="Shipment's Name", format="Not Null, maxlength = 200", required=true)
	private String code;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="receivedby")
	@ApiObjectField(description="Receive User", required=true)
	private User receivedby;

	@Column(nullable=true)
	@ApiObjectField(description="receivedate", required=false)
	private Calendar receivedate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="recordedby")
	@ApiObjectField(description="Recording User", required=true)
	private User recordedby;

	@Column(nullable=true)
	@ApiObjectField(description="recorddate", required=false)
	private Calendar recorddate;

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

	public User getRecordedby() {
		return recordedby;
	}

	public void setRecordedby(User recordedby) {
		this.recordedby = recordedby;
	}

	public Calendar getRecorddate() {
		return recorddate;
	}

	public void setRecorddate(Calendar recorddate) {
		this.recorddate = recorddate;
	}

}
