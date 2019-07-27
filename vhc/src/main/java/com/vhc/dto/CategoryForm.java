package com.vhc.dto;

import java.sql.Blob;

import com.vhc.core.model.Category;
import com.vhc.util.ImageProcessor;

public class CategoryForm {

	private long categoryid;

	private String name;

	private String title;

	private Blob image;

	private String imageData;


	public CategoryForm() {

	}

	public CategoryForm(Category category) {
		this.categoryid = category.getCategoryid();
		this.name = category.getName();
		this.image = category.getImage();
		this.title = category.getTitle();

		if(category.getImage() != null) {
			ImageProcessor processor = new ImageProcessor();
			this.imageData = processor.getImageByteArray(category.getImage());
		} else {
			this.imageData = null;
		}
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

	public String getImageData() {
		return imageData;
	}

	public void setImageData(String imageData) {
		this.imageData = imageData;
	}



}
