package com.vhc.core.model;

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
import javax.persistence.Table;
import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="USER_ROLES")
@NamedQuery(name="Userrole.findAll", query="SELECT s FROM Userrole s")
public class Userrole implements Serializable {

	private static final long serialVersionUID = -9192955485972775449L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "userroleid", updatable = false, nullable = false)
	private long userroleid;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userid")
	@ApiObjectField(description="Unique User", format="Not Null", required=true)
	@JsonBackReference
	private User user;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="roleid")
	@ApiObjectField(description="Unique Role", format="Not Null", required=false)
	private Role role;


	public long getUserroleid() {
		return userroleid;
	}

	public void setUserroleid(long userroleid) {
		this.userroleid = userroleid;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
