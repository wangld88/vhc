package com.vhc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="templates")
@NamedQuery(name="Template.findAll", query="SELECT t FROM Template t")
public class Template implements Serializable {

	private static final long serialVersionUID = -4470852701811206458L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "templateid", updatable = false, nullable = false)
	private int templateid;

	private String subject;

	private String description;

	private String bcc;

	private String cc;

	private String textcontent;

	private String htmlcontent;

	private String recipient;

	private String sender;

	private String servicename;

	private String methodname;


	public Template() {
	}

	public int getTemplateid() {
		return this.templateid;
	}

	public void setTemplateid(int templateid) {
		this.templateid = templateid;
	}

	public String getBcc() {
		return this.bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getCc() {
		return this.cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getTextcontent() {
		return this.textcontent;
	}

	public void setTextcontent(String textcontent) {
		this.textcontent = textcontent;
	}

	public String getHtmlcontent() {
		return htmlcontent;
	}

	public void setHtmlcontent(String htmlcontent) {
		this.htmlcontent = htmlcontent;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSender() {
		return this.sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public String getMethodname() {
		return methodname;
	}

	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}

}
