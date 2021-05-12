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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;


@Entity
@Table(name="cities", uniqueConstraints={@UniqueConstraint(columnNames = {"name" , "provinceid"})})
@ApiObject
//@NamedQuery(name="City.findAll", query="SELECT c FROM City c")
public class City implements Serializable {

	private static final long serialVersionUID = -2787102776094092844L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "cityid", updatable = false, nullable = false)
	private long cityid;

	@Column(nullable=false, unique=true, length=100)
	@Size(max=100)
	@ApiObjectField(description="Unique Name", format="Not Null, maxlength = 100", required=true)
	private String name;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="provinceid")
	@ApiObjectField(description="Unique Province", required=false)
	private Province province;

	public City() {

	}

	public long getCityid() {
		return cityid;
	}

	public void setCityid(long cityid) {
		this.cityid = cityid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

}
