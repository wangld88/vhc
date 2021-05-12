package com.vhc.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.jsondoc.core.annotation.ApiObjectField;


@Entity
@Table(name="tags")
@NamedQuery(name="Tag.findAll", query="SELECT s FROM Tag s")
public class Tag implements Serializable {

	private static final long serialVersionUID = 5060002369112824529L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "tagid", updatable = false, nullable = false)
	private long tagid;

	@Column(nullable=true, length=200)
	@Size(max=200)
	@ApiObjectField(description="Tag Name", format="maxlength = 200", required=false)
	private String name;

	@Column(nullable=true, length=400)
	@Size(max=400)
	@ApiObjectField(description="Comment notes", format="maxlength = 400", required=false)
	private String comments;


	public Tag() {

	}

	public long getTagid() {
		return tagid;
	}

	public void setTagid(long tagid) {
		this.tagid = tagid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
