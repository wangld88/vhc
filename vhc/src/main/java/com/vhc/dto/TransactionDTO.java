package com.vhc.dto;

import java.util.Calendar;

import com.vhc.core.model.Account;
import com.vhc.core.model.Invoice;
import com.vhc.core.model.Payment;
import com.vhc.core.model.Transaction;
import com.vhc.core.model.Type;
import com.vhc.core.model.User;

public class TransactionDTO {

	private long transactionid;

	private User recordedby;

	private Calendar recorddate;

	private String comments;

	private Account account;

	private Invoice invoice;

	private Payment payment;

	private Type type;

	public TransactionDTO() {

	}

	public TransactionDTO(Transaction tx) {
		this.transactionid = tx.getTransactionid();
		this.recordedby = tx.getRecordedby();
		this.recorddate = tx.getRecorddate();
		this.comments = tx.getComments();
		this.account = tx.getAccount();
		this.invoice = tx.getInvoice();
		this.payment = tx.getPayment();
		this.type = tx.getType();
	}

	public long getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(long transactionid) {
		this.transactionid = transactionid;
	}

	public User getRecordedby() {
		return recordedby;
	}

	public void setRecordedby(User recordedby) {
		this.recordedby = recordedby;
	}

	public Calendar getRecorddate() {
		return recorddate;
	}

	public void setRecorddate(Calendar recorddate) {
		this.recorddate = recorddate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
