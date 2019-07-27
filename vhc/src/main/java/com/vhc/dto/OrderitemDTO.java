package com.vhc.dto;

import java.math.BigDecimal;

import com.vhc.core.model.Inventory;
import com.vhc.core.model.Item;
import com.vhc.core.model.Order;
import com.vhc.core.model.Orderitem;
import com.vhc.core.model.Promocode;
import com.vhc.core.model.Type;

public class OrderitemDTO {

	private long orderitemid;
	private long quantity;
	private Promocode promocode;
	private BigDecimal amount;
	private long tax;
	private Item item;
	private Order order;
	private Type type;
	private Inventory inventory;


	public OrderitemDTO() {

	}

	public OrderitemDTO(Orderitem orderitem) {
		this.orderitemid = orderitem.getOrderitemid();
		this.quantity = orderitem.getQuantity();
		this.promocode = orderitem.getPromocodeid();
		this.amount = orderitem.getAmount();
		this.tax = orderitem.getTax();
		this.item = orderitem.getItem();
		this.order = orderitem.getOrder();
		this.type = orderitem.getType();
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

	public Promocode getPromocode() {
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

	public long getTax() {
		return tax;
	}

	public void setTax(long tax) {
		this.tax = tax;
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

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

}
