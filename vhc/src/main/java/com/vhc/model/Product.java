package com.vhc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name="PRODUCTS")
@ApiObject
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {

	private static final long serialVersionUID = -2385234332215716043L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "productid", updatable = false, nullable = false)
	private long productid;

	@Column(nullable=false, unique=true, length=200)
	@Size(max=200)
	@ApiObjectField(description="Manufacture's Name", format="Not Null, maxlength = 200", required=true)
	private String name;

	@Column(nullable=true, length=40)
	@Size(max=40)
	@ApiObjectField(description="Model Number", format="maxlength = 40", required=false)
	private String modelnum;

	@Column(nullable=true, length=50)
	@Size(max=50)
	@ApiObjectField(description="Universal Product Code", format="maxlength = 50", required=false)
	private String upc;

	@Column(nullable=true, length=400)
	@Size(max=400)
	@ApiObjectField(description="Description", format="maxlength = 400", required=false)
	private String description;

	@Column(nullable=true, length=10, columnDefinition="Decimal(6,2)")
	@ApiObjectField(description="Wholesale price", format="maxlength = 10", required=false)
	private BigDecimal wholesale;

	@Column(nullable=true, length=10, columnDefinition="Decimal(6,2)")
	@ApiObjectField(description="Retail price", format="maxlength = 10", required=false)
	private BigDecimal retail;

	@Column(nullable=true, length=10, columnDefinition="Decimal(6,2)")
	@ApiObjectField(description="Clinic price", format="maxlength = 10", required=false)
	private BigDecimal clinic;

	@Column(nullable=true, length=10, columnDefinition="Decimal(6,2)")
	@ApiObjectField(description="On Sale price", format="maxlength = 10", required=false)
	private BigDecimal onsale;

	@Column(nullable=true)
	@ApiObjectField(description="Bonus points", required=false)
	private Long points;

	@Column(nullable=true, length=80)
	@Size(max=80)
	@ApiObjectField(description="Material", format="maxlength = 80", required=false)
	private String material;

	@Column(nullable=true, length=1)
	@Size(max=1)
	@ApiObjectField(description="Flag of Store Front", format="maxlength = 1", required=false)
	private String storefront;

	@Column(nullable=true, length=600)
	@Size(max=600)
	@ApiObjectField(description="Commnents", format="maxlength = 600", required=false)
	private String comments;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="brandid")
	@ApiObjectField(description="Unique Brand", required=true)
	private Brand brand;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="typeid")
	@ApiObjectField(description="Product Type", required=true)
	private Type type;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="colorid")
	@ApiObjectField(description="Product Color", required=true)
	private Color color;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="styleid")
	@ApiObjectField(description="Style", required=false)
	private Style style;

	@OneToMany(cascade={CascadeType.ALL}, mappedBy="product", fetch=FetchType.LAZY)
	@JsonManagedReference
	private List<Image> images = new ArrayList<>();

	@JsonIgnore
	@ManyToMany(mappedBy="products", fetch=FetchType.LAZY)
	private List<Category> categories = new ArrayList<>();

	@Transient
	private BigDecimal finalprice;

	public Product() {

	}

	public long getProductid() {
		return productid;
	}

	public void setProductid(long productid) {
		this.productid = productid;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public String getModelnum() {
		return modelnum;
	}

	public void setModelnum(String modelnum) {
		this.modelnum = modelnum;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public BigDecimal getWholesale() {
		return wholesale;
	}

	public void setWholesale(BigDecimal wholesale) {
		this.wholesale = wholesale;
	}

	public BigDecimal getRetail() {
		return retail;
	}

	public void setRetail(BigDecimal retail) {
		this.retail = retail;
	}

	public BigDecimal getClinic() {
		return clinic;
	}

	public void setClinic(BigDecimal clinic) {
		this.clinic = clinic;
	}

	public Long getPoints() {
		return points;
	}

	public void setPoints(Long points) {
		this.points = points;
	}

	public BigDecimal getOnsale() {
		return onsale;
	}

	public void setOnsale(BigDecimal onsale) {
		this.onsale = onsale;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public String getStorefront() {
		return storefront;
	}

	public void setStorefront(String storefront) {
		this.storefront = storefront;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public BigDecimal getFinalprice() {
		if(this.onsale != null && onsale.compareTo(BigDecimal.ZERO) > 0) {
			finalprice = onsale;
		} else {
			finalprice = retail;
		}
		return finalprice;
	}

	public void setFinalprice() {
		if(onsale != null && onsale.compareTo(BigDecimal.ZERO) > 0) {
			finalprice = onsale;
		} else {
			finalprice = retail;
		}
	}
}
