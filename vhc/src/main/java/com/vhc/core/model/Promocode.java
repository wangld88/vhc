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
@Table(name="promocodes")
@NamedQuery(name="Promocode.findAll", query="SELECT s FROM Promocode s")
public class Promocode implements Serializable {

	private static final long serialVersionUID = -7487191444256254309L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "promocodeid", updatable = false, nullable = false)
	private long promocodeid;

	@Column(nullable=true, length=100)
	@Size(max=100)
	@ApiObjectField(description="Promotion code", format="maxlength = 100", required=false)
	private String code;

	@Column(nullable=true)
	@ApiObjectField(description="Promotion percentage", required=true)
	private long percentage;

	@Column(nullable=true, length=200)
	@Size(max=200)
	@ApiObjectField(description="Promotion description", format="maxlength = 200", required=false)
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	@ApiObjectField(description="Promotion start date", format="Not Null", required=true)
	private Calendar startdate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	@ApiObjectField(description="Promotion code expiration date", format="Not Null", required=true)
	private Calendar enddate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="updatedby")
	@ApiObjectField(description="Updated by User", required=true)
	private User updatedby;


	/*@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="storeid")
	@ApiObjectField(description="Unique Store", required=true)
	private Store store;*/


	public Promocode() {

	}

	public long getPromocodeid() {
		return promocodeid;
	}

	public void setPromocodeid(long promocodeid) {
		this.promocodeid = promocodeid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getPercentage() {
		return percentage;
	}

	public void setPercentage(long percentage) {
		this.percentage = percentage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Calendar getStartdate() {
		return startdate;
	}

	public void setStartdate(Calendar startdate) {
		this.startdate = startdate;
	}

	public Calendar getEnddate() {
		return enddate;
	}

	public void setEnddate(Calendar enddate) {
		this.enddate = enddate;
	}

	public User getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(User updatedby) {
		this.updatedby = updatedby;
	}


}
