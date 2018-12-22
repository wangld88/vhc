package com.vhc.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.vhc.model.Item;
import com.vhc.model.Product;
import com.vhc.model.Size;

public class ShopItem implements Serializable {

	private static final long serialVersionUID = -2679305900561353905L;

	private long itemid;
	private String sku;
	private double cost;
	private double price;
	private long quantity;
	private BigDecimal wholesale;
	private BigDecimal retail;
	private BigDecimal clinic;
	private BigDecimal onsale;
	private Long points;
	private long productid;
	private String name;
	private String modelnum;
	private String upc;
	private String description;
	private long sizeid;
	private String sizenum;
	private String region;
	private int total;


	public long getSizeid() {
		return sizeid;
	}


	public void setSizeid(long sizeid) {
		this.sizeid = sizeid;
	}


	public ShopItem() {

	}


	public ShopItem(Item item) {

		Product p = item.getProduct();
		this.itemid = item.getItemid();
		this.sku = item.getSku();
		this.cost = item.getCost();
		this.price = item.getPrice();
		this.quantity = item.getQuantity();
		this.wholesale = p.getWholesale();
		this.retail = p.getRetail();
		this.clinic = p.getClinic();
		this.onsale = p.getOnsale();
		this.points = p.getPoints();
		this.productid = p.getProductid();
		this.name = p.getName();
		this.modelnum = p.getModelnum();
		this.upc = p.getUpc();
		this.description = p.getDescription();
		this.sizenum = item.getSize().getSizenum();
		this.sizeid = item.getSize().getSizeid();
		this.region = item.getSize().getRegion().getCode();

		if(onsale != null && onsale != BigDecimal.ZERO) {
			price = onsale.doubleValue();
		} else if(retail != null && retail != BigDecimal.ZERO) {
			price = retail.doubleValue();
		}
	}

	public String getRegion() {
		return region;
	}


	public void setRegion(String region) {
		this.region = region;
	}


	public Item getItem() {
		Item item = new Item();
		item.setItemid(itemid);
		item.setSku(sku);
		item.setCost(cost);
		item.setPrice(price);
		item.setQuantity(quantity);
		Product p = new Product();
		p.setProductid(productid);
		item.setProduct(p);
		Size s = new Size();
		s.setSizeid(sizeid);
		item.setSize(s);

		return item;
	}


	public long getItemid() {
		return itemid;
	}


	public void setItemid(long itemid) {
		this.itemid = itemid;
	}


	public String getSku() {
		return sku;
	}


	public void setSku(String sku) {
		this.sku = sku;
	}


	public double getCost() {
		return cost;
	}


	public void setCost(double cost) {
		this.cost = cost;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public long getQuantity() {
		return quantity;
	}


	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}


	public String getSizenum() {
		return sizenum;
	}


	public void setSizenum(String sizenum) {
		this.sizenum = sizenum;
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


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}

}
