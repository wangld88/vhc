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
import javax.validation.constraints.Size;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;


@Entity
@Table(name="transactions")
@ApiObject
@NamedQuery(name="Transaction.findAll", query="SELECT T FROM Transaction T")
public class Transaction implements Serializable {

	private static final long serialVersionUID = -2905936313882022766L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "transactionid", updatable = false, nullable = false)
	private long transactionid;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="recordedby")
	@ApiObjectField(description="Recording User", required=true)
	private User recordedby;

	@Column(nullable=true)
	@ApiObjectField(description="recorddate", required=false)
	private Calendar recorddate;

	@Column(nullable=true, length=600)
	@Size(max=600)
	@ApiObjectField(description="Transaction comments", format="maxlength = 600", required=false)
	private String comments;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="accountid")
	@ApiObjectField(description="Transaction account", required=true)
	private Account account;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="invoiceid")
	@ApiObjectField(description="Transaction Invoice", required=true)
	private Invoice invoice;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="paymentid")
	@ApiObjectField(description="Transaction payment", required=true)
	private Payment payment;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="typeid")
	@ApiObjectField(description="Transaction Type", required=true)
	private Type type;

	public Transaction(){

	}

	public long getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(long transactionid) {
		this.transactionid = transactionid;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
