package com.vhc.core.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.jsondoc.core.annotation.ApiObjectField;


@Entity
@Table(name="ORDERS")
@NamedQuery(name="Order.findAll", query="SELECT s FROM Order s")
public class Order implements Serializable {

	private static final long serialVersionUID = -7487191444256254309L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "orderid", updatable = false, nullable = false)
	private long orderid;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="customerid")
	@ApiObjectField(description="Unique Status", required=true)
	private Customer customer;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="storeid")
	@ApiObjectField(description="Unique Store", required=true)
	private Store store;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="staffid")
	@ApiObjectField(description="Staff on this order", required=true)
	private Staff staff;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	@ApiObjectField(description="User's creationdate", format="Not Null", required=true)
	private Calendar creationdate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="createdby")
	@ApiObjectField(description="Created User", required=true)
	private User createdby;

	@Column(name="amount", precision=13, scale=2)
	@ApiObjectField(description="Order amount", format="Not Null", required=true)
	private BigDecimal amount;

	@Column(nullable=true, length=400)
	@Size(max=400)
	@ApiObjectField(description="Comment notes", format="maxlength = 400", required=false)
	private String detail;

	@OneToMany(cascade={CascadeType.ALL}, mappedBy="order", fetch=FetchType.LAZY)
	private List<Orderitem> orderitems;

	@OneToMany(cascade={CascadeType.ALL}, mappedBy="order", fetch=FetchType.LAZY)
	private List<Invoice> invoices;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, name = "shipmethodid")
	private Shippingmethod shippingmethod;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="recipientid")
	@ApiObjectField(description="Unique Recipient", format="Not Null", required=false)
	private Recipient recipient;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="statusid")
	@ApiObjectField(description="Order Status", required=true)
	private Status status;


	public Order() {

	}

	public long getOrderid() {
		return orderid;
	}

	public void setOrderid(long orderid) {
		this.orderid = orderid;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public List<Orderitem> getOrderitems() {
		return orderitems;
	}

	public void setOrderitems(List<Orderitem> orderitems) {
		this.orderitems = orderitems;
	}

    public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	public Shippingmethod getShippingmethod() {
		return shippingmethod;
	}

	public void setShippingmethod(Shippingmethod shippingmethod) {
		this.shippingmethod = shippingmethod;
	}

	public Recipient getRecipient() {
		return recipient;
	}

	public void setRecipient(Recipient recipient) {
		this.recipient = recipient;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
