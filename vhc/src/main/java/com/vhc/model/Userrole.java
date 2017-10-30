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
import javax.persistence.Table;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@Entity
@Table(name="USER_ROLE")
@ApiObject
public class Userrole implements Serializable {
	
	private static final long serialVersionUID = -9192955485972775449L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "usrlid", updatable = false, nullable = false)
	private long usrlid;
  
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userid")
	@ApiObjectField(description="Unique User", format="Not Null, maxlength = 30", required=true)
	private User user;
  
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="roleid")
	@ApiObjectField(description="Unique Role", format="Not Null, maxlength = 30", required=false)
	private Role role;

	
	public long getUsrlid() {
		return usrlid;
	}

	public void setUsrlid(long usrlid) {
		this.usrlid = usrlid;
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
