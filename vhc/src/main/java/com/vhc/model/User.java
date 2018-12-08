package com.vhc.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
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

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name="USERS")
@NamedQuery(name="User.findAll", query="SELECT s FROM User s")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "userid", updatable = false, nullable = false)
	private long userid;

	@Column(nullable=false, unique=true, length=40)
	@Size(max=40)
	@ApiObjectField(description="Unique Username", format="Not Null, maxlength = 40", required=true)
	private String username;

	@Column(nullable=false, length=40)
	@Size(max=40)
	@ApiObjectField(description="User's First Name", format="Not Null, maxlength = 40", required=true)
	private String firstname;

	@Column(nullable=false, length=40)
	@Size(max=40)
	@ApiObjectField(description="User's Last Name", format="Not Null, maxlength = 40", required=true)
	private String lastname;

	@Column(nullable=false, length=10)
	@Size(max=10)
	@ApiObjectField(description="User's Gender", format="Not Null, maxlength = 10", required=true)
	private String gender;

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
	@ApiObjectField(description="User's creationdate", format="Not Null", required=true)
	private Calendar creationdate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="createdby")
	@ApiObjectField(description="Created User", required=true)
	private User createdby;

	@Temporal(TemporalType.DATE)
	@Column(nullable=true)
	@ApiObjectField(description="User's update date", required=false)
	private Calendar updatedate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="updatedby")
	@ApiObjectField(description="Updated by User", required=false)
	private User updatedby;

	@OneToMany(cascade={javax.persistence.CascadeType.ALL}, mappedBy="user", fetch=FetchType.EAGER)
	@JsonManagedReference
	private List<Userrole> userroles;


	public User() {

	}

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

	public User getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(User createdby) {
		this.createdby = createdby;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public boolean hasRole(String role) {
		for(int i = 0; i < userroles.size(); i++) {
			if(userroles.get(i).getRole().getName().equals(role)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		String rtn = "";
		rtn = this.firstname + ", " + lastname;
		//rtn = ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
		return rtn;
	}
}
