package com.vhc.model;

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
@Table(name="PAYMENTMETHODS")
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

}
