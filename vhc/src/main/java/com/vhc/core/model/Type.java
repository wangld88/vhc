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
@Table(name="TYPES")
@ApiObject
@NamedQuery(name="Type.findAll", query="SELECT t FROM Type t")
public class Type implements Serializable {

	private static final long serialVersionUID = 8794993608772238695L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "typeid", updatable = false, nullable = false)
	private long typeid;

	@Column(nullable=false, unique=true, length=20)
	@Size(max=20)
	@ApiObjectField(description="Type's Name", format="Not Null, maxlength = 20", required=true)
	private String name;

	@Column(nullable=false, unique=true, length=30)
	@Size(max=30)
	@ApiObjectField(description="reference table's Name", format="Not Null, maxlength = 30", required=true)
	private String reftbl;



	public Type() {

	}

	public long getTypeid() {
		return typeid;
	}

	public void setTypeid(long typeid) {
		this.typeid = typeid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReftbl() {
		return reftbl;
	}

	public void setReftbl(String reftbl) {
		this.reftbl = reftbl;
	}

}
