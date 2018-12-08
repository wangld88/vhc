package com.vhc.model;

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
import javax.validation.constraints.Size;

import org.jsondoc.core.annotation.ApiObjectField;


@Entity
@Table(name="DEBITCARDS")
@NamedQuery(name="Debitcard.findAll", query="SELECT s FROM Debitcard s")
public class Debitcard implements Serializable {

	private static final long serialVersionUID = 7476538263507187583L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "debitcardid", updatable = false, nullable = false)
	private long debitcardid;

	@Column(nullable=false, length=20)
	@Size(max=20)
	@ApiObjectField(description="Card's number", format="maxlength = 20", required=true)
	private String cardnum;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="customerid")
	@ApiObjectField(description="Owned by Customer", required=true)
	private Customer customer;


	public Debitcard() {

	}

	public Debitcard(String cardnum) {
		this.cardnum = cardnum;
	}

	public Debitcard(String cardnum, Customer customer) {
		this.cardnum = cardnum;
		this.customer = customer;
	}

	public long getDebitcardid() {
		return debitcardid;
	}

	public void setDebitcardid(long debitcardid) {
		this.debitcardid = debitcardid;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCardnum() {
		return cardnum;
	}

	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}

}
