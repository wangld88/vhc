package com.vhc.core.model;

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
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vhc.core.util.ImageProcessor;


@Entity
@Table(name="PAGEIMAGES")
@NamedQuery(name="Pageimage.findAll", query="SELECT s FROM Pageimage s")
public class Pageimage implements Serializable {

	private static final long serialVersionUID = 7246449179647332290L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "pageimageid", updatable = false, nullable = false)
	private long pageimageid;

	@Column(nullable=true, length=200)
	@Size(max=200)
	@ApiObjectField(description="Image name", format="maxlength = 200", required=false)
	private String name;

	@Column(nullable=true, length=200)
	@Size(max=200)
	@ApiObjectField(description="Image description", format="maxlength = 200", required=false)
	private String description;

	@Column(nullable=true)
	@Lob
	private Blob image;

	@Column(nullable=false)
	@ApiObjectField(description="Image sequence number")
	private long seqnum;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="pageid")
	@ApiObjectField(description="Unique Page", required=true)
	@JsonBackReference
	private Page page;

	@Transient
	private String imagedata;


	public Pageimage() {

	}


	public long getPageimageid() {
		return pageimageid;
	}


	public void setPageimageid(long pageimageid) {
		this.pageimageid = pageimageid;
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


	public Blob getImage() {
		return image;
	}


	public void setImage(Blob image) {
		this.image = image;
	}


	public long getSeqnum() {
		return seqnum;
	}


	public void setSeqnum(long seqnum) {
		this.seqnum = seqnum;
	}


	public String getImagedata() {
		ImageProcessor processor = new ImageProcessor();
		this.imagedata = processor.getImageByteArray(getImage());
		return imagedata;
	}


	public void setImagedata(String imagedata) {
		this.imagedata = imagedata;
	}

	public Page getPage() {
		return page;
	}


	public void setPage(Page page) {
		this.page = page;
	}

}
