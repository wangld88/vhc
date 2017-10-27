package com.vhc.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import org.jsondoc.core.annotation.ApiObjectField;


@Entity
@Table(name="USERS")
@NamedQuery(name="User.findAll", query="SELECT s FROM User s")
public class User
 	implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="USERS_USERID_GENERATOR", sequenceName="USER_SEQ")
	@GeneratedValue(generator="USERS_USERID_GENERATOR")
	private long userid;
	
	@Column(nullable=true, unique=true, length=40)
	@Size(max=40)
	@ApiObjectField(description="Unique Username", format="Not Null, maxlength = 40", required=false)
	private String username;
	
	@Column(nullable=false, length=40)
	@Size(max=40)
	@ApiObjectField(description="User's First Name", format="Not Null, maxlength = 40", required=true)
	private String firstname;
	
	@Column(nullable=false, length=40)
	@Size(max=40)
	@ApiObjectField(description="User's Last Name", format="Not Null, maxlength = 40", required=true)
	private String lastname;
	
	@Column(nullable=false, length=80)
	@Size(max=80)
	@ApiObjectField(description="User's Email", format="Not Null, maxlength = 80", required=true)
	private String email;
	
	@Column(nullable=false, length=20)
	@Size(max=20)
	@ApiObjectField(description="User's Phone", format="Not Null, maxlength = 20", required=true)
	private String phone;
	
	@Column(nullable=false, length=20)
	@Size(max=20)
	@ApiObjectField(description="User's Cell", format="Not Null, maxlength = 20", required=true)
	private String cell;
	
	@Column(nullable=false, length=60)
	@Size(max=60)
	@ApiObjectField(description="User's password", format="Not Null, maxlength = 60", required=true)
	private String password;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	@ApiObjectField(description="User's creationdate", format="Not Null, maxlength = 60", required=true)
	private Calendar creationdate;
	
	@Column(nullable=false, length=60)
	@ApiObjectField(description="User's createdby", required=true)
	private String createdby;
	
	@OneToMany(cascade={javax.persistence.CascadeType.ALL}, mappedBy="user", fetch=FetchType.EAGER)
	private List<Userrole> userroles;
  
	public long getUserid() {
		return this.userid;
	}
  
	public void setUserid(long userid) {
		this.userid = userid;
	}
  
	public String getUsername() {
		return this.username;
	}
  
	public void setUsername(String username) {
		this.username = username;
	}
  
	public String getFirstname() {
		return this.firstname;
	}
  
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
  
	public String getLastname() {
		return this.lastname;
	}
  
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
  
	public String getEmail() {
		return this.email;
	}
  
	public void setEmail(String email) {
		this.email = email;
	}
  
	public String getPhone() {
		return this.phone;
	}
  
	public void setPhone(String phone) {
		this.phone = phone;
	}
  
	public String getCell() {
		return this.cell;
	}
  
	public void setCell(String cell) {
		this.cell = cell;
	}
  
	public String getPassword() {
		return this.password;
	}
  
	public void setPassword(String password) {
		this.password = password;
	}
  
	public Calendar getCreationdate() {
		return this.creationdate;
	}
  
	public void setCreationdate(Calendar creationdate) {
		this.creationdate = creationdate;
	}
  
	public String getCreatedby() {
		return this.createdby;
	}
  
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
  
	public List<Userrole> getUserroles() {
		return this.userroles;
	}
  
	public void setUserroles(List<Userrole> userroles) {
		this.userroles = userroles;
	}
  
	public String[] getRoles() {
		String[] roleList = new String[userroles.size()];
		for(int i = 0; i < userroles.size(); i++) {
			roleList[i] = userroles.get(i).getRole().getName();
		}
		return roleList;
	}
}
