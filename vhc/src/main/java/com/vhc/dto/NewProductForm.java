package com.vhc.dto;

import java.math.BigDecimal;
import java.util.List;

import com.vhc.core.model.Brand;
import com.vhc.core.model.Color;
import com.vhc.core.model.Product;
import com.vhc.core.model.Producttag;
import com.vhc.core.model.Style;
import com.vhc.core.model.Type;

public class NewProductForm {

	private long productid;
	private String name;
	private String modelnum;
	private String upc;
	private String description;
	private BigDecimal wholesale;
	private BigDecimal retail;
	private BigDecimal clinic;
	private BigDecimal onsale;
	private Long points;
	private String material;
	private String storefront;
	private String comments;
	private Brand brand;
	private Type type;
	private Color color;
	private Style style;
	private int total;

	private List<ImageForm> images;

	private List<Producttag> tags;


	public NewProductForm() {
	}

	public NewProductForm(Product product) {
		this.productid = product.getProductid();
		this.name = product.getName();
		this.modelnum = product.getModelnum();
		this.upc = product.getUpc();
		this.description = product.getDescription();
		this.wholesale = product.getWholesale();
		this.retail = product.getRetail();
		this.clinic = product.getClinic();
		this.onsale = product.getOnsale();
		this.points = product.getPoints();
		this.material = product.getMaterial();
		this.storefront = product.getStorefront();
		this.comments = product.getComments();
		this.brand = product.getBrand();
		this.type = product.getType();
		this.color = product.getColor();
		this.style = product.getStyle();
	}

	public List<ImageForm> getImages() {
		return images;
	}

	public void setImages(List<ImageForm> images) {
		this.images = images;
	}

	public List<Producttag> getTags() {
		return tags;
	}

	public void setTags(List<Producttag> tags) {
		this.tags = tags;
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

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
