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

import org.jsondoc.core.annotation.ApiObjectField;


@Entity
@Table(name="regions")
@NamedQuery(name="Region.findAll", query="SELECT b FROM Region b")
public class Region implements Serializable {

	private static final long serialVersionUID = -2787102756094092834L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "regionid", updatable = false, nullable = false)
	private long regionid;

	@Column(nullable=false, unique=true, length=10)
	@Size(max=10)
	@ApiObjectField(description="Region's Name", format="Not Null, maxlength = 10", required=true)
	private String name;

	@Column(nullable=false, unique=true, length=2)
	@Size(max=2)
	@ApiObjectField(description="Region's Code", format="Not Null, maxlength = 2", required=true)
	private String code;


	public Region() {

	}

	public long getRegionid() {
		return regionid;
	}

	public void setRegionid(long regionid) {
		this.regionid = regionid;
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
}
