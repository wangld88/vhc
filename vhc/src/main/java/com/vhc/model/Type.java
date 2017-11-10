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
@Table(name="TYPES")
@ApiObject
@NamedQuery(name="Type.findAll", query="SELECT t FROM Type t")
public class Type implements Serializable {

	private static final long serialVersionUID = 8794993608772238695L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "typeid", updatable = false, nullable = false)
	private long typeid;
	
	@Column(nullable=false, unique=true, length=200)
	@Size(max=200)
	@ApiObjectField(description="Manufacture's Name", format="Not Null, maxlength = 200", required=true)
	private String name;
	

	public Type () {
		
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

}
