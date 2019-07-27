package com.vhc.core.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@Entity
@Table(name="ROLES")
@ApiObject
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "roleid", updatable = false, nullable = false)
	private long roleid;

	@Column(nullable=true, unique=true, length=20)
	@Size(max=20)
	@ApiObjectField(description="Unique name", format="Not Null, maxlength = 20", required=false)
	private String name;

	
	public Role() {
		
	}
	
	public long getRoleid() {
		return this.roleid;
	}
  
	public void setRoleid(long roleid) {
		this.roleid = roleid;
	}
  
	public String getName() {
		return this.name;
	}
  
	public void setName(String name) {
		this.name = name;
	}
	
}
