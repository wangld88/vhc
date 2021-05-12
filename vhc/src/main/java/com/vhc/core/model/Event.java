package com.vhc.core.model;

import java.io.Serializable;
import java.util.Calendar;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.jsondoc.core.annotation.ApiObjectField;


@Entity
@Table(name="events")
@NamedQuery(name="Event.findAll", query="SELECT s FROM Event	 s")
public class Event implements Serializable {

	private static final long serialVersionUID = -2644467874148036202L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "eventid", updatable = false, nullable = false)
	private long eventid;

	@Column(nullable=false, length=200)
	@Size(max=200)
	@ApiObjectField(description="Event Name", format="Not Null, maxlength = 200", required=true)
	private String name;

	@Column(nullable=true, unique=false, length=800)
	@Size(max=800)
	@ApiObjectField(description="Event Description", format="Not Null, maxlength = 800", required=false)
	private String description;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="statusid")
	@ApiObjectField(description="Unique Status", required=true)
	private Status status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	@ApiObjectField(description="User's creationdate", format="Not Null", required=true)
	private Calendar startdate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	@ApiObjectField(description="User's creationdate", format="Not Null", required=true)
	private Calendar enddate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	@ApiObjectField(description="User's creationdate", format="Not Null", required=true)
	private Calendar creationdate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="createdby")
	@ApiObjectField(description="Created User", required=true)
	private User createdby;

	public Event() {

	}

	public long getEventid() {
		return eventid;
	}

	public void setEventid(long eventid) {
		this.eventid = eventid;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Calendar getStartdate() {
		return startdate;
	}

	public void setStartdate(Calendar startdate) {
		this.startdate = startdate;
	}

	public Calendar getEnddate() {
		return enddate;
	}

	public void setEnddate(Calendar enddate) {
		this.enddate = enddate;
	}

	public Calendar getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Calendar creationdate) {
		this.creationdate = creationdate;
	}

	public User getCreatedby() {
		return createdby;
	}

	public void setCreatedby(User createdby) {
		this.createdby = createdby;
	}

}
