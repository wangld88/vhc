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

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;


@Entity
@Table(name="RECIPIENTS")
@ApiObject
@NamedQuery(name="Recipient.findAll", query="SELECT p FROM Recipient p")
public class Recipient implements Serializable {

	private static final long serialVersionUID = -4975854097966329290L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "recipientid", updatable = false, nullable = false)
	private long recipientid;

	@Column(nullable=false, length=40)
	@Size(max=40)
	@ApiObjectField(description="Recipient's First Name", format="Not Null, maxlength = 40", required=true)
	private String firstname;

	@Column(nullable=false, length=40)
	@Size(max=40)
	@ApiObjectField(description="Recipient's Last Name", format="Not Null, maxlength = 40", required=true)
	private String lastname;

	@Column(nullable=false, length=10)
	@Size(max=10)
	@ApiObjectField(description="Recipient's Title", format="Not Null, maxlength = 10", required=true)
	private String title;

	@Column(nullable=false, length=80)
	@Size(max=80)
	@ApiObjectField(description="Recipient's Email", format="Not Null, maxlength = 80", required=true)
	private String email;

	@Column(nullable=false, length=20)
	@Size(max=20)
	@ApiObjectField(description="Recipient's Phone", format="Not Null, maxlength = 20", required=true)
	private String phone;

	@Column(nullable=false, length=20)
	@Size(max=20)
	@ApiObjectField(description="Recipient's Cell", format="Not Null, maxlength = 20", required=true)
	private String cell;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	@ApiObjectField(description="Recipient's creationdate", format="Not Null", required=true)
	private Calendar creationdate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="createdby")
	@ApiObjectField(description="Created Recipient", required=true)
	private Customer createdby;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	@ApiObjectField(description="Recipient's update date", required=false)
	private Calendar updatedate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="updatedby")
	@ApiObjectField(description="Updated by Recipient", required=false)
	private Customer updatedby;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="addressid")
	@ApiObjectField(description="Unique Address", format="Not Null", required=false)
	private Address address;


	public Recipient() {

	}

	public Recipient(Customer customer) {
		this.address = customer.getAddress();
		this.firstname = customer.getUser().getFirstname();
		this.lastname = customer.getUser().getLastname();
		this.phone = customer.getUser().getPhone();
		this.cell = customer.getUser().getCell();
		this.email = customer.getUser().getEmail();
		this.createdby = customer;
	}

	public long getRecipientid() {
		return recipientid;
	}

	public void setRecipientid(long recipientid) {
		this.recipientid = recipientid;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public Calendar getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Calendar creationdate) {
		this.creationdate = creationdate;
	}

	public Customer getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Customer createdby) {
		this.createdby = createdby;
	}

	public Calendar getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Calendar updatedate) {
		this.updatedate = updatedate;
	}

	public Customer getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(Customer updatedby) {
		this.updatedby = updatedby;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
