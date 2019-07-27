package com.vhc.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;


@Entity
@Table(name="PROVINCES", uniqueConstraints={@UniqueConstraint(columnNames = {"name" , "countryid"})})
@ApiObject
@NamedQuery(name="Province.findAll", query="SELECT p FROM Province p")
public class Province implements Serializable {

	private static final long serialVersionUID = -5171824612699336274L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "provinceid", updatable = false, nullable = false)
	private long provinceid;

	@Column(nullable=false, length=100)
	@Size(max=100)
	@ApiObjectField(description="Unique Name", format="Not Null, maxlength = 100", required=true)
	private String name;

	@Column(nullable=true, unique=true, length=3)
	@Size(max=3)
	@ApiObjectField(description="Unique Code", format="maxlength = 3", required=false)
	private String code;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="countryid")
	@ApiObjectField(description="Unique Country", required=false)
	private Country country;


	public Province() {

	}

	public long getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(long provinceid) {
		this.provinceid = provinceid;
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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

}
