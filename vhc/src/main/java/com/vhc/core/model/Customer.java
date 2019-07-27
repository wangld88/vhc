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

import com.vhc.core.model.User;


@Entity
@Table(name="CUSTOMERS")
@NamedQuery(name="Customer.findAll", query="SELECT s FROM Customer s")
public class Customer implements Serializable {

	private static final long serialVersionUID = -2750234919804600622L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "customerid", updatable = false, nullable = false)
	private long customerid;

	@Column(nullable=true, length=600)
	@Size(max=600)
	@ApiObjectField(description="Comment notes", format="maxlength = 600", required=false)
	private String comments;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	@ApiObjectField(description="User's creationdate", format="Not Null", required=true)
	private Calendar creationdate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userid", insertable=false, updatable=false)
	@ApiObjectField(description="Created User", required=true)
	private User createdby;

	@Temporal(TemporalType.DATE)
	@Column(nullable=true)
	@ApiObjectField(description="User's update date", required=false)
	private Calendar updatedate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userid", insertable=false, updatable=false)
	@ApiObjectField(description="Updated by User", required=false)
	private User updatedby;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "userid", insertable=false, updatable=false)
	private User user;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="addressid")
	@ApiObjectField(description="Unique Address", format="Not Null", required=false)
	private Address address;


	public Customer() {

	}

	public long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(long customerid) {
		this.customerid = customerid;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public Calendar getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Calendar updatedate) {
		this.updatedate = updatedate;
	}

	public User getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(User updatedby) {
		this.updatedby = updatedby;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
