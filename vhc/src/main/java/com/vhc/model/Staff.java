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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.jsondoc.core.annotation.ApiObjectField;

@Entity
@Table(name="STAFFS")
@NamedQuery(name="Staff.findAll", query="SELECT s FROM Staff s")
public class Staff implements Serializable {

	private static final long serialVersionUID = -9192905465972375649L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "staffid", updatable = false, nullable = false)
	private long staffid;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userid")
	@ApiObjectField(description="Unique User", format="Not Null", required=true)
	private User user;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="storeid")
	@ApiObjectField(description="Unique Store", format="Not Null", required=false)
	private Store store;


	public long getStaffid() {
		return staffid;
	}

	public void setStaffid(long staffid) {
		this.staffid = staffid;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Store getStore() {
		return this.store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

}
