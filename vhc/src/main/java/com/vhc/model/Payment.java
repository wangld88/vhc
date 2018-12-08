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

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;


@Entity
@Table(name="PAYMENTS")
@ApiObject
@NamedQuery(name="Payment.findAll", query="SELECT p FROM Payment p")
public class Payment implements Serializable {

	private static final long serialVersionUID = 2634527108522897654L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "paymentid", updatable = false, nullable = false)
	private long paymentid;

	@Column(name="amount", precision=13, scale=2)
	@ApiObjectField(description="Payment amount", format="Not Null", required=true)
	private BigDecimal amount;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	@ApiObjectField(description="Payment's creation date", format="Not Null", required=true)
	private Calendar creationdate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="createdby")
	@ApiObjectField(description="Payment created by User", required=true)
	private User createdby;

	@Column(nullable=true, length=600)
	@Size(max=600)
	@ApiObjectField(description="Comment notes", format="maxlength = 600", required=false)
	private String comments;

	@Column(name="creditamount", precision=13, scale=2)
	@ApiObjectField(description="Payment through credit card", required=false)
	private BigDecimal creditamount;

	@Column(name="debitamount", precision=13, scale=2)
	@ApiObjectField(description="Payment through debit card", required=false)
	private BigDecimal debitamount;

	@Column(name="giftamount", precision=13, scale=2)
	@ApiObjectField(description="Payment through gift card", required=false)
	private BigDecimal giftamount;

	@Column(name="cashamount", precision=13, scale=2)
	@ApiObjectField(description="Payment through gift card", required=false)
	private BigDecimal cashamount;


	public Payment() {

	}


	public long getPaymentid() {
		return paymentid;
	}


	public void setPaymentid(long paymentid) {
		this.paymentid = paymentid;
	}


	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}


	public BigDecimal getCreditamount() {
		return creditamount;
	}


	public void setCreditamount(BigDecimal creditamount) {
		this.creditamount = creditamount;
	}


	public BigDecimal getDebitamount() {
		return debitamount;
	}


	public void setDebitamount(BigDecimal debitamount) {
		this.debitamount = debitamount;
	}


	public BigDecimal getGiftamount() {
		return giftamount;
	}


	public void setGiftamount(BigDecimal giftamount) {
		this.giftamount = giftamount;
	}


	public BigDecimal getCashamount() {
		return cashamount;
	}


	public void setCashamount(BigDecimal cashamount) {
		this.cashamount = cashamount;
	}


}
