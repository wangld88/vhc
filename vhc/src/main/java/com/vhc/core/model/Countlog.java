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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.jsondoc.core.annotation.ApiObjectField;


@Entity
@Table(name="countlogs")
@NamedQuery(name="Countlog.findAll", query="SELECT c FROM Countlog c")
public class Countlog implements Serializable {

	private static final long serialVersionUID = 8840115965763660494L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "countlogid", updatable = false, nullable = false)
	private long countlogid;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="countuploadid")
	@ApiObjectField(description="Count upload ID", required=true)
	private Countupload upload;

	@Column(nullable=false, length=40)
	@Size(max=40)
	@ApiObjectField(description="Item UPC number", format="maxlength = 40", required=true)
	private String sku;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "inventoryid")
	@ApiObjectField(description="Inventory ID", required=true)
	private Inventory inventory;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	@ApiObjectField(description="User's creationdate", format="Not Null", required=true)
	private Calendar creationdate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="createdby")
	@ApiObjectField(description="Created User", required=true)
	private User createdby;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	@ApiObjectField(description="User's update date", required=false)
	private Calendar updatedate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="updatedby", insertable=false, updatable=false)
	@ApiObjectField(description="Updated by User", required=false)
	private User updatedby;

	public Countlog() {

	}

	public long getCountlogid() {
		return countlogid;
	}

	public void setCountlogid(long countlogid) {
		this.countlogid = countlogid;
	}

	public Countupload getUpload() {
		return upload;
	}

	public void setUpload(Countupload upload) {
		this.upload = upload;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Calendar getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Calendar creationdate) {
		this.creationdate = creationdate;
	}

	public User getCreatedby() {
		return createdby;
	}

	public void setCreatedby(User createdby) {
		this.createdby = createdby;
	}

	public Calendar getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Calendar updatedate) {
		this.updatedate = updatedate;
	}

	public User getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(User updatedby) {
		this.updatedby = updatedby;
	}

}
