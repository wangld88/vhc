package com.vhc.dto;

import java.sql.Blob;

import com.vhc.model.Image;
import com.vhc.model.Product;
import com.vhc.util.ImageProcessor;

public class ImageForm {

	private long imageid;

	private String name;

	//private Blob image;

	//private Product product;

	private String imageData;

	public ImageForm() {

	}

	public ImageForm(Image image) {
		this.imageid = image.getImageid();
		this.name = image.getName();
		//this.image = image.getImage();
		//this.product = image.getProduct();
		ImageProcessor processor = new ImageProcessor();
		this.imageData = processor.getImageByteArray(image.getImage());
	}

	public long getImageid() {
		return imageid;
	}

	public void setImageid(long imageid) {
		this.imageid = imageid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}*/

	public String getImageData() {
		return imageData;
	}

	public void setImageData(String imageData) {
		this.imageData = imageData;
	}
}
