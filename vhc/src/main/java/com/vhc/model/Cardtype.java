package com.vhc.model;

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
@Table(name="CARDTYPES")
@NamedQuery(name="Cardtype.findAll", query="SELECT c FROM Cardtype c")
public class Cardtype implements Serializable {

	private static final long serialVersionUID = -80670237149126617L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "cardtypeid", updatable = false, nullable = false)
	private long cardtypeid;

	@Column(nullable=false, length=30)
	@Size(max=30)
	@ApiObjectField(description="Credit Card type Name", format="Not Null, maxlength = 30", required=true)
	private String name;

}
