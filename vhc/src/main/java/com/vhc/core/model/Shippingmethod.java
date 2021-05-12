package com.vhc.core.model;

import java.io.Serializable;
import java.math.BigDecimal;

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
@Table(name="shippingmethods")
@NamedQuery(name="Shippingmethod.findAll", query="SELECT s FROM Shippingmethod s")
public class Shippingmethod implements Serializable {

	private static final long serialVersionUID = -1462667295026076394L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "shipmethodid", updatable = false, nullable = false)
	private long shipmethodid;

	@Column(nullable=true, length=100)
	@Size(max=100)
	@ApiObjectField(description="Name", format="maxlength = 100", required=false)
	private String name;

	@Column(nullable=true, length=400)
	@Size(max=400)
	@ApiObjectField(description="Description", format="maxlength = 400", required=false)
	private String description;

	@Column(name="cost", precision=13, scale=2)
	@ApiObjectField(description="Shipping cost", format="Not Null", required=true)
	private BigDecimal cost;


	public Shippingmethod() {

	}

	public long getShipmethodid() {
		return shipmethodid;
	}

	public void setShipmethodid(long shipmethodid) {
		this.shipmethodid = shipmethodid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

}
