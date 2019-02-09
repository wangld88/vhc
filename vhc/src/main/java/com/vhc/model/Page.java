package com.vhc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name="PAGES")
@ApiObject
@NamedQuery(name="Page.findAll", query="SELECT c FROM Page c")
public class Page implements Serializable {

	private static final long serialVersionUID = 3377805478071665661L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "pageid", updatable = false, nullable = false)
	private long pageid;

	@Column(nullable=true, length=100)
	@Size(max=100)
	@ApiObjectField(description="Page Name", format="maxlength = 100", required=false)
	private String name;

	@Column(nullable=true, length=100)
	@Size(max=100)
	@ApiObjectField(description="Page Title", format="maxlength = 100", required=false)
	private String title;

	@Column(nullable=true, length=250)
	@Size(max=250)
	@ApiObjectField(description="Page Link", format="maxlength = 250", required=false)
	private String link;

	@Column(nullable=false)
	@ApiObjectField(description="Image Width", required=true)
	private long imgwidth;

	@Column(nullable=false)
	@ApiObjectField(description="Image Height", required=true)
	private long imgheight;

	@Column(nullable=true, length=400)
	@Size(max=400)
	@ApiObjectField(description="Web Page", format="maxlength = 400", required=false)
	private String comments;

	@OneToMany(cascade={CascadeType.ALL}, mappedBy="page", fetch=FetchType.LAZY)
	@JsonManagedReference
	private List<Pageimage> pageimages = new ArrayList<>();


	public Page() {

	}

	public long getPageid() {
		return pageid;
	}

	public void setPageid(long pageid) {
		this.pageid = pageid;
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public long getImgwidth() {
		return imgwidth;
	}

	public void setImgwidth(long imgwidth) {
		this.imgwidth = imgwidth;
	}

	public long getImgheight() {
		return imgheight;
	}

	public void setImgheight(long imgheight) {
		this.imgheight = imgheight;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<Pageimage> getPageimages() {
		return pageimages;
	}

	public void setPageimages(List<Pageimage> pageimages) {
		this.pageimages = pageimages;
	}

}
