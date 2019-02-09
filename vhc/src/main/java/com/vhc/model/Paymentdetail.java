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
import javax.validation.constraints.Size;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;


@Entity
@Table(name="PAYMENTDETAILS")
@ApiObject
@NamedQuery(name="Paymentdetail.findAll", query="SELECT p FROM Paymentdetail p")
public class Paymentdetail implements Serializable {

	private static final long serialVersionUID = 8812410958399407237L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "detailid", updatable = false, nullable = false)
	private long detailid;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="paymentid")
	@ApiObjectField(description="Transaction payment", required=true)
	private Payment payment;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="methodid")
	@ApiObjectField(description="Payment method", format="Not Null", required=false)
	private Paymentmethod paymentmethod;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="statusid")
	@ApiObjectField(description="Payment Status", required=true)
	private Status status;

	@Column(name="received", precision=13, scale=2)
	@ApiObjectField(description="Payment received", format="Not Null", required=true)
	private BigDecimal received;

	@Column(name="amount", precision=13, scale=2)
	@ApiObjectField(description="Payment amount", format="Not Null", required=true)
	private BigDecimal amount;

	@Column(name="backchange", precision=13, scale=2)
	@ApiObjectField(description="Payment back change", format="Not Null", required=true)
	private BigDecimal backchange;

	@Column(nullable=true, length=100)
	@Size(max=100)
	@ApiObjectField(description="Payment reference number (credit card ID etc.)", format="maxlength = 100", required=false)
	private String refnum;

	@Column(nullable=true, length=100)
	@Size(max=100)
	@ApiObjectField(description="Payment transaction result/no.", format="maxlength = 100", required=false)
	private String trxnum;

	private Calendar paydate;


	public Paymentdetail() {

	}

	public long getDetailid() {
		return detailid;
	}

	public void setDetailid(long detailid) {
		this.detailid = detailid;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Paymentmethod getPaymentmethod() {
		return paymentmethod;
	}

	public void setPaymentmethod(Paymentmethod paymentmethod) {
		this.paymentmethod = paymentmethod;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRefnum() {
		return refnum;
	}

	public void setRefnum(String refnum) {
		this.refnum = refnum;
	}

	public String getTrxnum() {
		return trxnum;
	}

	public void setTrxnum(String trxnum) {
		this.trxnum = trxnum;
	}

	public Calendar getPaydate() {
		return paydate;
	}

	public void setPaydate(Calendar paydate) {
		this.paydate = paydate;
	}

	public BigDecimal getReceived() {
		return received;
	}

	public void setReceived(BigDecimal received) {
		this.received = received;
	}

	public BigDecimal getBackchange() {
		return backchange;
	}

	public void setBackchange(BigDecimal backchange) {
		this.backchange = backchange;
	}


}
