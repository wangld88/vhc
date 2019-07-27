package com.vhc.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.jsondoc.core.annotation.ApiObjectField;

@Entity
@Table(name="STATUS")
@NamedQuery(name="Status.findAll", query="SELECT s FROM Status s")
public class Status implements Serializable {

	private static final long serialVersionUID = 7702824128277503095L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "statusid", updatable = false, nullable = false)
	private long statusid;

	@Column(nullable=false, unique=true, length=40)
	@Size(max=40)
	@ApiObjectField(description="Status' Name", format="Not Null, maxlength = 40", required=true)
	private String name;

	@Column(nullable=true, unique=true, length=3)
	@Size(max=3)
	@ApiObjectField(description="Unique Code", format="Not Null, maxlength = 3", required=false)
	private String code;

	@Column(nullable=true, unique=true, length=40)
	@Size(max=40)
	@ApiObjectField(description="Reference Table", format="Not Null, maxlength = 40", required=false)
	private String reftbl;


	public long getStatusid() {
		return statusid;
	}

	public void setStatusid(long statusid) {
		this.statusid = statusid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReftbl() {
		return reftbl;
	}

	public void setReftbl(String reftbl) {
		this.reftbl = reftbl;
	}

}
