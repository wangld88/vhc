package com.vhc.dto;

import java.util.List;

import com.vhc.model.Item;

public class ProductForm {

	private long inventoryid;

	private long quantity;
	
	private Item item;

	private List<ImageForm> images;
	
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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<ImageForm> getImages() {
		return images;
	}

	public void setImages(List<ImageForm> images) {
		this.images = images;
	}
	
	
}
