package com.vhc.dto;

import java.util.Calendar;
import java.util.List;

import com.vhc.core.model.User;
import com.vhc.core.model.Userrole;


public class UserForm {

	private long userid;
	private User createdby;
	private Calendar creationdate;
	private String firstname;
	private String lastname;
	private String email;
	private String phone;
	private String cell;
	private String password;
	private String confirmpassword;
	private String status;
	private String username;
	private List<Userrole> roles;

	public UserForm() {

	}

	public UserForm(User user) {

		this.userid = user.getUserid();
		this.username = user.getUsername();
		this.firstname = user.getFirstname();
		this.lastname = user.getLastname();
		this.email = user.getEmail();
		this.phone = user.getPhone();
		this.password = user.getPassword();
		this.createdby = user.getCreatedby();
		this.creationdate = user.getCreationdate();
		this.roles = user.getUserroles();
		//this.status = user.getStatus();

	}

	public User getUser() {

		User user = new User();

		user.setUserid(this.userid);
		user.setUsername(this.username);
		user.setFirstname(this.firstname);
		user.setLastname(this.lastname);
		user.setEmail(this.email);
		user.setPhone(this.phone);
		user.setCell(this.cell);
		user.setPassword(this.password);
		user.setCreatedby(this.createdby);
		user.setCreationdate(this.creationdate);
		//user.setStatus(this.status);

		return user;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public User getCreatedby() {
		return createdby;
	}
	public void setCreatedby(User createdby) {
		this.createdby = createdby;
	}

	public Calendar getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Calendar creationdate) {
		this.creationdate = creationdate;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone(){
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCell(){
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}


	public List<Userrole> getRoles() {
		return roles;
	}

	public void setRoles(List<Userrole> roles) {
		this.roles = roles;
	}

}
