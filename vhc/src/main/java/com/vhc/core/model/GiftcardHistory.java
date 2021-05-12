package com.vhc.core.model;

import java.io.Serializable;
import java.math.BigDecimal;
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

import org.jsondoc.core.annotation.ApiObjectField;


@Entity
@Table(name="giftcardhistories")
@NamedQuery(name="GiftcardHistory.findAll", query="SELECT s FROM GiftcardHistory s")
public class GiftcardHistory implements Serializable {

	private static final long serialVersionUID = -7487191474256284309L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "gfhistoryid", updatable = false, nullable = false)
	private long gfhistoryid;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="giftcardid")
	@ApiObjectField(description="Gift card", required=true)
	private Giftcard giftcard;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="refid")
	@ApiObjectField(description="Gift card", required=true)
	private Giftcard refcard;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="trxid")
	@ApiObjectField(description="Transaction", required=true)
	private Transaction trxn;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="typeid")
	@ApiObjectField(description="Transaction Type", required=true)
	private Type type;

	@Column(name="amount", precision=13, scale=2)
	@ApiObjectField(description="Initial load amount", format="Not Null", required=true)
	private BigDecimal amount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	@ApiObjectField(description="Card's load date", format="Not Null", required=true)
	private Calendar operatedate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="operatedby")
	@ApiObjectField(description="Loaded by User", required=true)
	private User operatedby;

	@Column(name="balance", precision=13, scale=2)
	@ApiObjectField(description="Card balance", format="Not Null", required=true)
	private BigDecimal balance;


	public GiftcardHistory() {

	}


	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public long getGfhistoryid() {
		return gfhistoryid;
	}


	public void setGfhistoryid(long gfhistoryid) {
		this.gfhistoryid = gfhistoryid;
	}


	public Giftcard getGiftcard() {
		return giftcard;
	}


	public void setGiftcard(Giftcard giftcard) {
		this.giftcard = giftcard;
	}


	public Giftcard getRefcard() {
		return refcard;
	}


	public void setRefcard(Giftcard refcard) {
		this.refcard = refcard;
	}


	public Transaction getTrxn() {
		return trxn;
	}


	public void setTrxn(Transaction trxn) {
		this.trxn = trxn;
	}


	public Type getType() {
		return type;
	}


	public void setType(Type type) {
		this.type = type;
	}


	public Calendar getOperatedate() {
		return operatedate;
	}


	public void setOperatedate(Calendar operatedate) {
		this.operatedate = operatedate;
	}


	public User getOperatedby() {
		return operatedby;
	}


	public void setOperatedby(User operatedby) {
		this.operatedby = operatedby;
	}


	public BigDecimal getBalance() {
		return balance;
	}


	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
