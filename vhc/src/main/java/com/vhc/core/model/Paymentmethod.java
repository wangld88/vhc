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
@Table(name="paymentmethods")
@ApiObject
@NamedQuery(name="Paymentmethod.findAll", query="SELECT p FROM Paymentmethod p")
public class Paymentmethod implements Serializable {

	private static final long serialVersionUID = 2634527108522317654L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "methodid", updatable = false, nullable = false)
	private long methodid;

	@Column(nullable=false, length=100)
	@Size(max=100)
	@ApiObjectField(description="Payment method name", format="maxlength = 100", required=true)
	private String name;

	@Column(nullable=false, length=10)
	@Size(max=10)
	@ApiObjectField(description="code", format="maxlength = 10", required=true)
	private String code;

	@Column(nullable=false, length=20)
	@Size(max=20)
	@ApiObjectField(description="type", format="maxlength = 20", required=true)
	private String type;


	public Paymentmethod() {

	}


	public long getMethodid() {
		return methodid;
	}


	public void setMethodid(long methodid) {
		this.methodid = methodid;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


}
