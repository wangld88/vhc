package com.vhc.core.model;

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
@Table(name="invoices")
@ApiObject
@NamedQuery(name="Invoice.findAll", query="SELECT p FROM Invoice p")
public class Invoice implements Serializable {

	private static final long serialVersionUID = 3277128274280447300L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "invoiceid", updatable = false, nullable = false)
	private long invoiceid;

	@Column(name="invoicenum", nullable=false, length=20)
	@Size(max=20)
	@ApiObjectField(description="Invoice number", format="maxlength = 20", required=true)
	private String invoicenum;

	@Column(name="amount", precision=13, scale=2)
	@ApiObjectField(description="Invoice amount", format="Not Null", required=true)
	private BigDecimal amount;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="orderid")
	@ApiObjectField(description="Invoice Order", required=true)
	private Order order;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	@ApiObjectField(description="Invoice's creation date", format="Not Null", required=true)
	private Calendar creationdate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="createdby")
	@ApiObjectField(description="Invoice created by User", required=true)
	private User createdby;

	@Column(nullable=true, length=600)
	@Size(max=600)
	@ApiObjectField(description="Comment notes", format="maxlength = 600", required=false)
	private String comments;

	@Column(name="barcode", nullable=false, length=40)
	@Size(max=40)
	@ApiObjectField(description="Bar Code", format="maxlength = 40", required=true)
	private String barcode;


	public Invoice() {

	}


	public long getInvoiceid() {
		return invoiceid;
	}


	public void setInvoiceid(long invoiceid) {
		this.invoiceid = invoiceid;
	}


	public String getInvoicenum() {
		return invoicenum;
	}


	public void setInvoicenum(String invoicenum) {
		this.invoicenum = invoicenum;
	}


	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public Order getOrder() {
		return order;
	}


	public void setOrder(Order order) {
		this.order = order;
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


	public String getBarcode() {
		return barcode;
	}


	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

}
