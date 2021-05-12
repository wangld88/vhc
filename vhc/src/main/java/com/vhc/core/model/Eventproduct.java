package com.vhc.core.model;

import java.io.Serializable;
import java.math.BigDecimal;

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


@Entity
@Table(name="event_products")
@NamedQuery(name="Eventproduct.findAll", query="SELECT i FROM Eventproduct i")
public class Eventproduct implements Serializable {

	private static final long serialVersionUID = 355590389201916907L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "eventprodid", updatable = false, nullable = false)
	private long eventprodid;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="eventid")
	@ApiObjectField(description="Unique Event", required=true)
	private Event event;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="productid")
	@ApiObjectField(description="Unique Product", required=true)
	private Product product;

	private int percentage;

	@Column(name="price", precision=13, scale=2)
	@ApiObjectField(description="event price", format="Not Null", required=true)
	private BigDecimal price;

	public Eventproduct() {

	}


	public long getEventprodid() {
		return eventprodid;
	}


	public void setEventprodid(long eventprodid) {
		this.eventprodid = eventprodid;
	}


	public Event getEvent() {
		return event;
	}


	public void setEvent(Event event) {
		this.event = event;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}


	public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
