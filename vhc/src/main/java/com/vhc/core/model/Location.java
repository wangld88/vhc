/**
 *
 */
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

import org.jsondoc.core.annotation.ApiObjectField;

/**
 * @author Jerry
 *
 */
@Entity
@Table(name="locations")
@NamedQuery(name="Location.findAll", query="SELECT b FROM Location b")
public class Location implements Serializable {

	private static final long serialVersionUID = 6976608535630014727L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "locationid", updatable = false, nullable = false)
	private long locationid;

	@Column(nullable=false, unique=true, length=100)
	@ApiObjectField(description="Location name", format="Not Null, maxlength = 100", required=true)
	private String name;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="storeid")
	@ApiObjectField(description="Location in Store", required=true)
	private Store store;


	public Location() {

	}

	public long getLocationid() {
		return locationid;
	}

	public void setLocationid(long locationid) {
		this.locationid = locationid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}


}
