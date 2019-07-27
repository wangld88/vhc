/**
 * 
 */
package com.vhc.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.jsondoc.core.annotation.ApiObjectField;

/**
 * @author Jerry
 *
 */
@Entity
@Table(name="SIZES")
@NamedQuery(name="Size.findAll", query="SELECT b FROM Size b")
public class Size implements Serializable {

	private static final long serialVersionUID = 6976608535630014727L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "sizeid", updatable = false, nullable = false)
	private long sizeid;
	
	@Column(nullable=false, unique=true, length=20)
	@ApiObjectField(description="size num", format="Not Null, maxlength = 20", required=true)
	private String sizenum;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="typeid")
	@ApiObjectField(description="Product Type", required=true)
	private Type type;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="regionid")
	@ApiObjectField(description="Region", required=true)
	private Region region;

	
	public Size() {
		
	}
	
	public long getSizeid() {
		return sizeid;
	}

	public void setSizeid(long sizeid) {
		this.sizeid = sizeid;
	}

	public String getSizenum() {
		return sizenum;
	}

	public void setSizenum(String sizenum) {
		this.sizenum = sizenum;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

}
