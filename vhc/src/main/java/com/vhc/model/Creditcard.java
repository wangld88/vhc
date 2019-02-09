package com.vhc.model;

import java.io.Serializable;

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
@Table(name="CREDITCARDS")
@NamedQuery(name="Creditcard.findAll", query="SELECT s FROM Creditcard s")
public class Creditcard implements Serializable {

	private static final long serialVersionUID = -7487191444256254309L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "creditcardid", updatable = false, nullable = false)
	private long creditcardid;

	@Column(nullable=false, length=60)
	@Size(max=60)
	@ApiObjectField(description="Card's name", format="maxlength = 60", required=true)
	private String printname;

	@Column(nullable=false, length=20)
	@Size(max=20)
	@ApiObjectField(description="Card's number", format="maxlength = 20", required=true)
	private String cardnum;

	@Column(nullable=true, length=5)
	@Size(max=5)
	@ApiObjectField(description="Card's cvv", format="maxlength = 5", required=false)
	private String cvv;

	@Column(nullable=false)
	@Size(max=2)
	@ApiObjectField(description="Card's expire month", format="Not Null", required=true)
	private String expiremonth;

	@Column(nullable=false)
	@Size(max=4)
	@ApiObjectField(description="Card's expire year", format="Not Null", required=true)
	private String expireyear;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="customerid")
	@ApiObjectField(description="Owned by Customer", required=true)
	private Customer customer;


	public Creditcard() {

	}

	public Creditcard(String cardnum) {
		this.cardnum = cardnum;
	}

	public Creditcard(String cardnum, Customer customer) {
		this.cardnum = cardnum;
		this.customer = customer;
	}

	public long getCreditcardid() {
		return creditcardid;
	}

	public void setCreditcardid(long creditcardid) {
		this.creditcardid = creditcardid;
	}

	public String getPrintname() {
		return printname;
	}

	public void setPrintname(String printname) {
		this.printname = printname;
	}

	public String getExpiremonth() {
		return expiremonth;
	}

	public void setExpiremonth(String expiremonth) {
		this.expiremonth = expiremonth;
	}

	public String getExpireyear() {
		return expireyear;
	}

	public void setExpireyear(String expireyear) {
		this.expireyear = expireyear;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCardnum() {
		return cardnum;
	}

	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

}
