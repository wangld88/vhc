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


@Entity
@Table(name="purchaseorders")
@NamedQuery(name="Purchaseorder.findAll", query="SELECT p FROM Purchaseorder p")
public class Purchaseorder implements Serializable {

	private static final long serialVersionUID = -4680176460239853214L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "poid", updatable = false, nullable = false)
	private long poid;

	@Column(nullable=false, unique=true, length=200)
	@Size(max=200)
	@ApiObjectField(description="Shipment's Name", format="Not Null, maxlength = 200", required=true)
	private String code;

	@Column(nullable=true)
	@ApiObjectField(description="expectdate", required=false)
	private Calendar expectdate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="sentby")
	@ApiObjectField(description="Receive User", required=true)
	private User sentby;

	@Column(nullable=true)
	@ApiObjectField(description="sentdate", required=false)
	private Calendar sentdate;

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

	@OneToMany(cascade={javax.persistence.CascadeType.ALL}, mappedBy="purchaseorder", fetch=FetchType.LAZY)
	@JsonBackReference
	private List<Item> items = new ArrayList<>();

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="statusid")
	@ApiObjectField(description="Order Status", required=true)
	private Status status;


	public Purchaseorder() {

	}

	public long getPoid() {
		return poid;
	}


	public void setPoid(long poid) {
		this.poid = poid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public User getSentby() {
		return sentby;
	}

	public void setSentby(User sentby) {
		this.sentby = sentby;
	}

	public Calendar getSentdate() {
		return sentdate;
	}

	public void setSentdate(Calendar sentdate) {
		this.sentdate = sentdate;
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

	public Calendar getExpectdate() {
		return expectdate;
	}

	public void setExpectdate(Calendar expectdate) {
		this.expectdate = expectdate;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
