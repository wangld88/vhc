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
import javax.validation.constraints.Size;

import org.jsondoc.core.annotation.ApiObjectField;



@Entity
@Table(name="countinventories")
@NamedQuery(name="Countinventory.findAll", query="SELECT s FROM Countinventory s")
public class Countinventory implements Serializable {

	private static final long serialVersionUID = -5842353933292605287L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "countinventoryid", updatable = false, nullable = false)
	private long countinventoryid;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "inventoryid")
	@ApiObjectField(description="Inventory ID", required=true)
	private Inventory inventory;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="countid")
	@ApiObjectField(description="Inventory count ID", required=true)
	private Inventorycount count;

	@Column(name="product", nullable=false, unique=true, length=200)
	@Size(max=200)
	@ApiObjectField(description="Manufacture's Name", format="Not Null, maxlength = 200", required=true)
	private String product;

	@Column(name="modelnum", nullable=true, length=40)
	@Size(max=40)
	@ApiObjectField(description="Model Number", format="maxlength = 40", required=false)
	private String modelnum;

	@Column(name="brand", nullable=false, length=200)
	@Size(max=200)
	@ApiObjectField(description="Brand Name", format="Not Null, maxlength = 200", required=true)
	private String brand;

	@Column(nullable=false, unique=true, length=30)
	@ApiObjectField(description="size num", format="Not Null, maxlength = 30", required=true)
	private String sizenum;

	@Column(nullable=true, length=20)
	@Size(max=20)
	@ApiObjectField(description="Unique sku", format="Not Null, maxlength = 20", required=false)
	private String sku;

	@Column(nullable=false, unique=true, length=200)
	@Size(max=200)
	@ApiObjectField(description="Store's Name", format="Not Null, maxlength = 200", required=true)
	private String store;

	@Column(nullable=false, unique=true, length=100)
	@ApiObjectField(description="Location name", format="Not Null, maxlength = 100", required=true)
	private String location;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="countlogid")
	@ApiObjectField(description="Inventory count log ID", required=false)
	private Countlog countlog;

	public Countinventory() {

	}

	public long getCountinventoryid() {
		return countinventoryid;
	}

	public void setCountinventoryid(long countinventoryid) {
		this.countinventoryid = countinventoryid;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Inventorycount getCount() {
		return count;
	}

	public void setCount(Inventorycount count) {
		this.count = count;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getModelnum() {
		return modelnum;
	}

	public void setModelnum(String modelnum) {
		this.modelnum = modelnum;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSizenum() {
		return sizenum;
	}

	public void setSizenum(String sizenum) {
		this.sizenum = sizenum;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Countlog getCountlog() {
		return countlog;
	}

	public void setCountlog(Countlog countlog) {
		this.countlog = countlog;
	}

}
