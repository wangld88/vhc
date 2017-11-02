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
@Table(name="ADDRESSES")
@NamedQuery(name="Address.findAll", query="SELECT a FROM Address a")
public class Address implements Serializable {

	private static final long serialVersionUID = 7899965418289768342L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "addressid", updatable = false, nullable = false)
	private long addressid;
	

	@Column(nullable=false, length=100)
	@Size(max=100)
	@ApiObjectField(description="Street Name", format="Not Null, maxlength = 100", required=true)
	private String street;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cityid")
	@ApiObjectField(description="Unique City", format="Not Null", required=true)
	private City city;

	@Column(nullable=false, length=10)
	@Size(max=10)
	@ApiObjectField(description="Postal Code", format="Not Null, maxlength = 10", required=true)
	private String postalcode;

	
	public Address() {
		
	}
	
	public Address(String street, City city, String postalcode) {
		this.street = street;
		this.city = city;
		this.postalcode = postalcode;
	}
	
	public long getAddressid() {
		return addressid;
	}

	public void setAddressid(long addressid) {
		this.addressid = addressid;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

}
