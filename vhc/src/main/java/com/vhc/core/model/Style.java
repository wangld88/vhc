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

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@Entity
@Table(name="styles")
@ApiObject
@NamedQuery(name="Style.findAll", query="SELECT r FROM Style r")
public class Style implements Serializable {

	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "styleid", updatable = false, nullable = false)
	private long styleid;

	@Column(nullable=true, unique=true, length=100)
	@Size(max=100)
	@ApiObjectField(description="Unique name", format="Not Null, maxlength = 100", required=false)
	private String name;

	@Column(nullable=true, length=1)
	@Size(max=1)
	@ApiObjectField(description="Sequence number", format="maxlength = 1", required=false)
	private String seqnum;


	public Style() {

	}

	public long getStyleid() {
		return this.styleid;
	}

	public void setStyleid(long styleid) {
		this.styleid = styleid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSeqnum() {
		return seqnum;
	}

	public void setSeqnum(String seqnum) {
		this.seqnum = seqnum;
	}
}
