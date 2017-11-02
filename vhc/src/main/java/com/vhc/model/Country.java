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


/**
 * 
 * @author Jerry
 *
 */
@Entity
@Table(name="COUNTRIES")
@ApiObject
@NamedQuery(name="Country.findAll", query="SELECT c FROM Country c")
public class Country implements Serializable {

	private static final long serialVersionUID = -5818451727489525929L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "countryid", updatable = false, nullable = false)
	private long countryid;
	         
	@Column(nullable=false, unique=true, length=100)
	@Size(max=100)
	@ApiObjectField(description="Unique Name", format="Not Null, maxlength = 100", required=true)
	private String name;

/*	@Column(nullable=true, unique=true, length=3)
	@Size(max=3)
	@ApiObjectField(description="Unique Name", format="Not Null, maxlength = 3", required=false)
	private String code;
*/
	
	public Country() {
		
	}
	
	public Country(String name) { //, String code) {
		this.name = name;
		//this.code = code;
	}
	
	public long getCountryid() {
		return countryid;
	}

	public void setCountryid(long countryid) {
		this.countryid = countryid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}*/

}
