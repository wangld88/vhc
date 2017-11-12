package com.vhc.model;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;


@Entity
@Table(name="IMAGES")
@ApiObject
@NamedQuery(name="Image.findAll", query="SELECT c FROM Image c")
public class Image implements Serializable {

	private static final long serialVersionUID = 3377805478071665661L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "imageid", updatable = false, nullable = false)
	private long imageid;
	
	@Column(nullable=true, length=200)
	@Size(max=200)
	@ApiObjectField(description="Image Name", format="maxlength = 200", required=false)
	private String name;
	
	@Column(nullable=true)
	@Lob
	private Blob image;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="productid")
	@ApiObjectField(description="Unique Product", required=true)
	private Product product;

	public Image() {
		
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

	public Blob getImage() {
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
	}

	
}
