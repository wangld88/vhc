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
@Table(name="COLORS")
@ApiObject
@NamedQuery(name="Color.findAll", query="SELECT r FROM Color r")
public class Color implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "colorid", updatable = false, nullable = false)
	private long colorid;

	@Column(nullable=true, unique=true, length=50)
	@Size(max=50)
	@ApiObjectField(description="Unique name", format="Not Null, maxlength = 50", required=false)
	private String name;

	
	public Color() {
		
	}
	
	public long getColorid() {
		return this.colorid;
	}
  
	public void setColorid(long colorid) {
		this.colorid = colorid;
	}
  
	public String getName() {
		return this.name;
	}
  
	public void setName(String name) {
		this.name = name;
	}
	
}
