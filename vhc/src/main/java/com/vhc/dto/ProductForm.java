package com.vhc.dto;

import java.util.List;

import com.vhc.model.Product;
import com.vhc.model.Producttag;

public class ProductForm {

	private Product product;

	private List<ImageForm> images;

	private List<Producttag> tags;


	public ProductForm() {
	}

	public ProductForm(Product product) {
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

}
