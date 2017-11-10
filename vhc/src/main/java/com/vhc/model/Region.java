package com.vhc.model;

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
@Table(name="REGIONS")
@NamedQuery(name="Region.findAll", query="SELECT b FROM Region b")
public class Region {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "regionid", updatable = false, nullable = false)
	private long regionid;
	
	@Column(nullable=false, unique=true, length=10)
	@Size(max=10)
	@ApiObjectField(description="Region's Name", format="Not Null, maxlength = 10", required=true)
	private String name;

	
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
}
