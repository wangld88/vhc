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
@Table(name="inventorycounts")
@NamedQuery(name="Inventorycount.findAll", query="SELECT s FROM Inventorycount s")
public class Inventorycount implements Serializable {

	private static final long serialVersionUID = -1840539653555962818L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "countid", updatable = false, nullable = false)
	private long countid;

	private long total;

	private long counted;

	private long scanned;

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

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="statusid")
	@ApiObjectField(description="Inventory count Status", required=true)
	private Status status;

	@Column(nullable=true, length=600)
	@Size(max=600)
	@ApiObjectField(description="Comment notes", format="maxlength = 600", required=false)
	private String comments;


	public Inventorycount() {

	}


	public long getCountid() {
		return countid;
	}


	public void setCountid(long countid) {
		this.countid = countid;
	}


	public long getScanned() {
		return scanned;
	}


	public void setScanned(long scanned) {
		this.scanned = scanned;
	}


	public long getTotal() {
		return total;
	}


	public void setTotal(long total) {
		this.total = total;
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


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}


	public long getCounted() {
		return counted;
	}


	public void setCounted(long counted) {
		this.counted = counted;
	}

}
