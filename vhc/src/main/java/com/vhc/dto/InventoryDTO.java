package com.vhc.dto;

import java.math.BigDecimal;
import java.util.Calendar;

import com.vhc.core.model.Inventory;
import com.vhc.core.model.Item;
import com.vhc.core.model.Product;


public class InventoryDTO {

	private long inventoryid;
	private long quantity;
	private String receivedby;
	private Calendar receivedate;
	private long storeid;
	private String storename;
	private long itemid;
	private String sku;
	private double cost;
	private double price;
	private String size;
	private long shipmentid;
	private long poid;
	private long productid;
	private String productname;
	private String modelnum;
	private String upc;
	private BigDecimal wholesale;
	private BigDecimal retail;
	private BigDecimal clinic;
	private BigDecimal onsale;
	private String brand;
	private String color;
	private String style;
	private String material;
	private BigDecimal finalprice;
	private Long tax;
	private String location;
	private long deststoreid;
	private String deststorename;
	private String sentby;
	private Calendar senddate;
	private String status;

	public InventoryDTO() {

	}

	public InventoryDTO(Inventory inventory) {
		this.inventoryid = inventory.getInventoryid();
		this.quantity = inventory.getQuantity();
		if(inventory.getReceivedby() != null) {
			this.receivedby = inventory.getReceivedby().getFirstname() + " " + inventory.getReceivedby().getLastname();
		}
		this.receivedate = inventory.getReceivedate();
		this.storeid = inventory.getStore().getStoreid();
		this.storename = inventory.getStore().getName();

		Item item = inventory.getItem();
		if(item != null) {
			this.itemid = item.getItemid();
			this.sku = item.getSku();
			this.cost = item.getCost();
			this.price = item.getPrice();
			if(item.getShipment() != null) {
				this.shipmentid = item.getShipment().getShipmentid();
			}
			if(item.getPurchaseorder() != null) {
				this.poid = item.getPurchaseorder().getPoid();
			}
			this.size = item.getSize().getRegion().getCode() + " " + item.getSize().getSizenum();
			Product product = item.getProduct();
			this.productid = product.getProductid();
			this.productname = product.getName();
			this.modelnum = product.getModelnum();
			this.upc = product.getUpc();
			this.wholesale = product.getWholesale();
			this.retail = product.getRetail();
			this.clinic = product.getClinic();
			this.onsale = product.getOnsale();
			this.finalprice = product.getFinalprice();
			this.brand = product.getBrand().getName();
			this.color = product.getColor().getName();
			this.style = product.getStyle().getName();
			this.material = product.getMaterial();
			this.tax = product.getTax();
		}

		if(inventory.getLocation() != null) {
			this.location = inventory.getLocation().getName();
		}
		if(inventory.getDeststore() != null) {
			this.deststoreid = inventory.getDeststore().getStoreid();
			this.deststorename = inventory.getDeststore().getName();
		}
		if(inventory.getSentby() != null) {
			this.sentby = inventory.getSentby().getFirstname() + " " + inventory.getSentby().getLastname();
		}
		this.senddate = inventory.getSenddate();
		this.status = inventory.getStatus().getName();
	}

	public long getInventoryid() {
		return inventoryid;
	}

	public void setInventoryid(long inventoryid) {
		this.inventoryid = inventoryid;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public String getReceivedby() {
		return receivedby;
	}

	public void setReceivedby(String receivedby) {
		this.receivedby = receivedby;
	}

	public Calendar getReceivedate() {
		return receivedate;
	}

	public void setReceivedate(Calendar receivedate) {
		this.receivedate = receivedate;
	}

	public long getStoreid() {
		return storeid;
	}

	public void setStoreid(long storeid) {
		this.storeid = storeid;
	}

	public String getStorename() {
		return storename;
	}

	public void setStorename(String storename) {
		this.storename = storename;
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

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public long getShipmentid() {
		return shipmentid;
	}

	public void setShipmentid(long shipmentid) {
		this.shipmentid = shipmentid;
	}

	public long getPoid() {
		return poid;
	}

	public void setPoid(long poid) {
		this.poid = poid;
	}

	public long getProductid() {
		return productid;
	}

	public void setProductid(long productid) {
		this.productid = productid;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
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

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public BigDecimal getFinalprice() {
		return finalprice;
	}

	public void setFinalprice(BigDecimal finalprice) {
		this.finalprice = finalprice;
	}

	public Long getTax() {
		return tax;
	}

	public void setTax(Long tax) {
		this.tax = tax;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public long getDeststoreid() {
		return deststoreid;
	}

	public void setDeststoreid(long deststoreid) {
		this.deststoreid = deststoreid;
	}

	public String getDeststorename() {
		return deststorename;
	}

	public void setDeststorename(String deststorename) {
		this.deststorename = deststorename;
	}

	public String getSentby() {
		return sentby;
	}

	public void setSentby(String sentby) {
		this.sentby = sentby;
	}

	public Calendar getSenddate() {
		return senddate;
	}

	public void setSenddate(Calendar senddate) {
		this.senddate = senddate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
