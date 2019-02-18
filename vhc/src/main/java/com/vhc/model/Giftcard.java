package com.vhc.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name="GIFTCARDS")
@NamedQuery(name="Giftcard.findAll", query="SELECT s FROM Giftcard s")
public class Giftcard implements Serializable {

	private static final long serialVersionUID = -7487191444256254309L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "giftcardid", updatable = false, nullable = false)
	private long giftcardid;

	@Column(nullable=true, length=100)
	@Size(max=100)
	@ApiObjectField(description="Card's code", format="maxlength = 100", required=false)
	private String code;

	@Column(nullable=true, length=20)
	@Size(max=20)
	@ApiObjectField(description="Card's pin", format="maxlength = 20", required=false)
	private String pin;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	@ApiObjectField(description="Card's load date", format="Not Null", required=true)
	private Calendar loaddate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="loadedby")
	@ApiObjectField(description="Loaded by User", required=true)
	private User loadedby;

	@Column(name="amount", precision=13, scale=2)
	@ApiObjectField(description="Initial load amount", format="Not Null", required=true)
	private BigDecimal amount;

	@Column(name="balance", precision=13, scale=2)
	@ApiObjectField(description="Card balance", format="Not Null", required=true)
	private BigDecimal balance;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="storeid")
	@ApiObjectField(description="Unique Store", required=true)
	private Store store;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="statusid")
	@ApiObjectField(description="Gift card Status", required=true)
	private Status status;


	public Giftcard() {

	}

	public long getGiftcardid() {
		return giftcardid;
	}

	public void setGiftcardid(long giftcardid) {
		this.giftcardid = giftcardid;
	}

	public Calendar getLoaddate() {
		return loaddate;
	}

	public void setLoaddate(Calendar loaddate) {
		this.loaddate = loaddate;
	}

	public User getLoadedby() {
		return loadedby;
	}

	public void setLoadedby(User loadedby) {
		this.loadedby = loadedby;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
