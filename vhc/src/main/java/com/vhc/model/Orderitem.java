package com.vhc.model;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.jsondoc.core.annotation.ApiObjectField;


@Entity
@Table(name="ORDER_ITEMS")
@NamedQuery(name="Orderitem.findAll", query="SELECT o FROM Orderitem o")
public class Orderitem implements Serializable {

	private static final long serialVersionUID = 5683561098504992238L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "orderitemid", updatable = false, nullable = false)
	private long orderitemid;

	@Column(nullable=false)
	@ApiObjectField(description="Quantity", required=true)
	private long quantity;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="promocodeid")
	@ApiObjectField(description="Promotion code", required=false)
	private Promocode promocode;

	@Column(name="amount", precision=13, scale=2)
	@ApiObjectField(description="Order amount", format="Not Null", required=true)
	private BigDecimal amount;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="itemid")
	@ApiObjectField(description="Unique Item", required=true)
	private Item item;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="orderid")
	@ApiObjectField(description="Unique Order", required=true)
	private Order order;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="typeid")
	@ApiObjectField(description="Order Type", required=true)
	private Type type;


	public Orderitem() {

	}


	public long getOrderitemid() {
		return orderitemid;
	}


	public void setOrderitemid(long orderitemid) {
		this.orderitemid = orderitemid;
	}


	public long getQuantity() {
		return quantity;
	}


	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}


	public Promocode getPromocodeid() {
		return promocode;
	}


	public void setPromocode(Promocode promocode) {
		this.promocode = promocode;
	}


	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public Item getItem() {
		return item;
	}


	public void setItem(Item item) {
		this.item = item;
	}


	public Order getOrder() {
		return order;
	}


	public void setOrder(Order order) {
		this.order = order;
	}


	public Type getType() {
		return type;
	}


	public void setType(Type type) {
		this.type = type;
	}

}
