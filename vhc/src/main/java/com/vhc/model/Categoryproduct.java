package com.vhc.model;

import java.io.Serializable;
import java.math.BigDecimal;

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


@Entity
@Table(name="CATEGORY_PRODUCTS")
@NamedQuery(name="Categoryproduct.findAll", query="SELECT o FROM Categoryproduct o")
public class Categoryproduct implements Serializable {

	private static final long serialVersionUID = 5683561098504992238L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "cateprodid", updatable = false, nullable = false)
	private long cateprodid;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="categoryid")
	@ApiObjectField(description="Unique Category", required=true)
	private Category category;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="productid")
	@ApiObjectField(description="Unique Product", required=true)
	private Product product;


	public Categoryproduct() {

	}


	public long getCateprodid() {
		return cateprodid;
	}


	public void setCateprodid(long cateprodid) {
		this.cateprodid = cateprodid;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
