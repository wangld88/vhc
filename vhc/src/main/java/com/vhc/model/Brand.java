package com.vhc.model;

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
import javax.validation.constraints.Size;

import org.jsondoc.core.annotation.ApiObjectField;


@Entity
@Table(name="BRANDS")
@NamedQuery(name="Brand.findAll", query="SELECT b FROM Brand b")
public class Brand implements Serializable {

	private static final long serialVersionUID = -596658205795166084L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "brandid", updatable = false, nullable = false)
	private long brandid;
	
	@Column(nullable=false, length=200)
	@Size(max=200)
	@ApiObjectField(description="Brand Name", format="Not Null, maxlength = 200", required=true)
	private String name;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="manufactureid")
	@ApiObjectField(description="Unique Manufacture", format="Not Null", required=true)
	private Manufacture manufacture;

	
	public Brand() {
		
	}
	
	public long getBrandid() {
		return brandid;
	}

	public void setBrandid(long brandid) {
		this.brandid = brandid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Manufacture getManufacture() {
		return manufacture;
	}

	public void setManufacture(Manufacture manufacture) {
		this.manufacture = manufacture;
	}

}
