package com.vhc.core.model;

import java.io.Serializable;
import java.sql.Blob;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Size;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="categories")
@ApiObject
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable {

	private static final long serialVersionUID = 3377805478071665661L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "categoryid", updatable = false, nullable = false)
	private long categoryid;

	@Column(nullable=true, length=200)
	@Size(max=200)
	@ApiObjectField(description="Category Name", format="maxlength = 200", required=false)
	private String name;

	@Column(nullable=true, length=100)
	@Size(max=100)
	@ApiObjectField(description="Category Title", format="maxlength = 100", required=false)
	private String title;

	@Column(nullable=true)
	@Lob
	private Blob image;

	@JsonIgnore
	@ManyToMany(cascade = {CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinTable(name = "category_products", joinColumns = @JoinColumn(name = "categoryid", referencedColumnName = "categoryid"), inverseJoinColumns = @JoinColumn(name = "productid", referencedColumnName = "productid"))
	private List<Product> products;


	public Category() {

	}

	public long getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(long categoryid) {
		this.categoryid = categoryid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
