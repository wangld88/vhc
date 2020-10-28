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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.jsondoc.core.annotation.ApiObjectField;


@Entity
@Table(name="ACCOUNTS")
@NamedQuery(name="Account.findAll", query="SELECT s FROM Account s")
public class Account implements Serializable {

	private static final long serialVersionUID = -8933958034210203689L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "accountid", updatable = false, nullable = false)
	private long accountid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	@ApiObjectField(description="User's creationdate", format="Not Null", required=true)
	private Calendar creationdate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="createdby")
	@ApiObjectField(description="Created User", required=true)
	private User createdby;

	@Column(nullable=true, length=400)
	@Size(max=400)
	@ApiObjectField(description="Comment notes", format="maxlength = 400", required=false)
	private String comments;

    @OneToOne(targetEntity = Customer.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "customerid")
	private Customer customer;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="statusid")
	@ApiObjectField(description="Account Status", required=true)
	private Status status;

	public Account() {

	}

	public long getAccountid() {
		return accountid;
	}

	public void setAccountid(long accountid) {
		this.accountid = accountid;
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
