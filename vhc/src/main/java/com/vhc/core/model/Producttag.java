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


@Entity
@Table(name="PRODUCT_TAGS")
@NamedQuery(name="Producttag.findAll", query="SELECT o FROM Producttag o")
public class Producttag implements Serializable {

	private static final long serialVersionUID = 1146917054237493593L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "prodtagid", updatable = false, nullable = false)
	private long prodtagid;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="productid")
	@ApiObjectField(description="Unique Product", required=true)
	private Product product;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="tagid")
	@ApiObjectField(description="Unique Tag", required=true)
	private Tag Tag;


	public Producttag() {

	}


	public long getProdtagid() {
		return prodtagid;
	}

	public void setProdtagid(long prodtagid) {
		this.prodtagid = prodtagid;
	}

	public Tag getTag() {
		return Tag;
	}

	public void setTag(Tag Tag) {
		this.Tag = Tag;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
