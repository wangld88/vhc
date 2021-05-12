package com.vhc.core.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@Entity
@Table(name="productview")
@ApiObject
@NamedQuery(name="Productview.findAll", query="SELECT p FROM Productview p")
public class Productview {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "productid", updatable = false, nullable = false)
	private long productid;

	@Column(nullable=false, unique=true, length=200)
	@Size(max=200)
	@ApiObjectField(description="Product's Name", format="Not Null, maxlength = 200", required=true)
	private String product;

	@Column(nullable=false, length=40)
	@Size(max=40)
	@ApiObjectField(description="Model Number", format="maxlength = 40", required=false)
	private String modelnum;

	@Column(nullable=false, length=50)
	@Size(max=50)
	@ApiObjectField(description="Universal Product Code", format="maxlength = 50", required=false)
	private String upc;

	@Column(nullable=false, length=400)
	@Size(max=400)
	@ApiObjectField(description="Description", format="maxlength = 400", required=false)
	private String description;

	@Column(nullable=false, length=10, columnDefinition="Decimal(6,2)")
	@ApiObjectField(description="Wholesale price", format="maxlength = 10", required=false)
	private BigDecimal wholesale;

	@Column(nullable=false, length=10, columnDefinition="Decimal(6,2)")
	@ApiObjectField(description="Retail price", format="maxlength = 10", required=false)
	private BigDecimal retail;

	@Column(nullable=false, length=10, columnDefinition="Decimal(6,2)")
	@ApiObjectField(description="Clinic price", format="maxlength = 10", required=false)
	private BigDecimal clinic;

	@Column(nullable=false, length=10, columnDefinition="Decimal(6,2)")
	@ApiObjectField(description="On Sale price", format="maxlength = 10", required=false)
	private BigDecimal onsale;

	@Column(nullable=false)
	@ApiObjectField(description="Bonus points", required=false)
	private Long points;

	@Column(nullable=false, length=80)
	@Size(max=80)
	@ApiObjectField(description="Material", format="maxlength = 80", required=false)
	private String material;

	@Column(nullable=false, length=1)
	@Size(max=1)
	@ApiObjectField(description="Flag of Store Front", format="maxlength = 1", required=false)
	private String storefront;

	@Column(nullable=false, length=1)
	@Size(max=1)
	@ApiObjectField(description="Flag of website display", format="maxlength = 1", required=false)
	private String display;

	@Column(nullable=false, length=600)
	@Size(max=600)
	@ApiObjectField(description="Commnents", format="maxlength = 600", required=false)
	private String comments;

	@Column(nullable=false)
	@ApiObjectField(description="Tax", required=false)
	private Long tax;

	@Column(nullable=false, length=1)
	@Size(max=1)
	@ApiObjectField(description="Sequence number", format="maxlength = 1", required=false)
	private String seqnum;

	@Column(name = "styleid", updatable = false)
	private long styleid;

	@Column(nullable=false, unique=true, length=100)
	@Size(max=100)
	@ApiObjectField(description="Unique style name", format="Not Null, maxlength = 100", required=false)
	private String style;

	@Column(name = "brandid", updatable = false, nullable = false)
	private long brandid;

	@Column(nullable=false, length=200)
	@Size(max=200)
	@ApiObjectField(description="Brand Name", format="Not Null, maxlength = 200", required=true)
	private String brand;

	@Column(name = "typeid", updatable = false, nullable = false)
	private long typeid;

	@Column(nullable=false, unique=true, length=20)
	@Size(max=20)
	@ApiObjectField(description="Type's Name", format="Not Null, maxlength = 20", required=true)
	private String type;

	@Column(name = "colorid", updatable = false, nullable = false)
	private long colorid;

	@Column(nullable=true, unique=true, length=50)
	@Size(max=50)
	@ApiObjectField(description="Unique name", format="Not Null, maxlength = 50", required=false)
	private String color;


	public void productview() {

	}


	public long getProductid() {
		return productid;
	}


	public void setProductid(long productid) {
		this.productid = productid;
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


	public String getUpc() {
		return upc;
	}


	public void setUpc(String upc) {
		this.upc = upc;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
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


	public BigDecimal getOnsale() {
		return onsale;
	}


	public void setOnsale(BigDecimal onsale) {
		this.onsale = onsale;
	}


	public Long getPoints() {
		return points;
	}


	public void setPoints(Long points) {
		this.points = points;
	}


	public String getMaterial() {
		return material;
	}


	public void setMaterial(String material) {
		this.material = material;
	}


	public String getStorefront() {
		return storefront;
	}


	public void setStorefront(String storefront) {
		this.storefront = storefront;
	}


	public String getDisplay() {
		return display;
	}


	public void setDisplay(String display) {
		this.display = display;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}


	public Long getTax() {
		return tax;
	}


	public void setTax(Long tax) {
		this.tax = tax;
	}


	public String getSeqnum() {
		return seqnum;
	}


	public void setSeqnum(String seqnum) {
		this.seqnum = seqnum;
	}


	public long getStyleid() {
		return styleid;
	}


	public void setStyleid(long styleid) {
		this.styleid = styleid;
	}


	public String getStyle() {
		return style;
	}


	public void setStyle(String style) {
		this.style = style;
	}


	public long getBrandid() {
		return brandid;
	}


	public void setBrandid(long brandid) {
		this.brandid = brandid;
	}


	public String getBrand() {
		return brand;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}


	public long getTypeid() {
		return typeid;
	}


	public void setTypeid(long typeid) {
		this.typeid = typeid;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public long getColorid() {
		return colorid;
	}


	public void setColorid(long colorid) {
		this.colorid = colorid;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}

}
