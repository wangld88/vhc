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
@Table(name="countuploads")
@NamedQuery(name="Countupload.findAll", query="SELECT c FROM Countupload c")
public class Countupload implements Serializable {

	private static final long serialVersionUID = 3253040611744578165L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "countuploadid", updatable = false, nullable = false)
	private long countuploadid;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="countid")
	@ApiObjectField(description="Inventory count ID", required=true)
	private Inventorycount count;

	@Column(nullable=false, length=40)
	@Size(max=40)
	@ApiObjectField(description="Upload file name", format="maxlength = 40", required=true)
	private String filename;

	private long total;

	private long counted;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	@ApiObjectField(description="User's creationdate", format="Not Null", required=true)
	private Calendar creationdate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="createdby")
	@ApiObjectField(description="Created User", required=true)
	private User createdby;

	public Countupload() {

	}

	public long getCountuploadid() {
		return countuploadid;
	}

	public void setCountuploadid(long countuploadid) {
		this.countuploadid = countuploadid;
	}

	public Inventorycount getCount() {
		return count;
	}

	public void setCount(Inventorycount count) {
		this.count = count;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getCounted() {
		return counted;
	}

	public void setCounted(long counted) {
		this.counted = counted;
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

}
