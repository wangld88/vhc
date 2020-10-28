package com.vhc.dto;

import com.vhc.core.model.Template;

public class TemplateForm {

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


	public TemplateForm() {

	}

	public TemplateForm(Template template) {
		this.templateid = template.getTemplateid();
		this.subject = template.getSubject();
		this.description = template.getDescription();
		this.sender = template.getSender();
		this.recipient = template.getRecipient();
		this.cc = template.getCc();
		this.bcc = template.getBcc();
		this.textcontent = template.getTextcontent();
		this.htmlcontent = template.getHtmlcontent();
		this.servicename = template.getServicename();
		this.methodname = template.getMethodname();
	}


	public int getTemplateid() {
		return templateid;
	}

	public void setTemplateid(int templateid) {
		this.templateid = templateid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getTextcontent() {
		return textcontent;
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

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
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

	public Template getTemplate() {
		Template template = new Template();

		template.setTemplateid(templateid);
		template.setSubject(subject);
		template.setDescription(description);
		template.setSender(sender);
		template.setRecipient(recipient);
		template.setCc(cc);
		template.setBcc(bcc);
		template.setTextcontent(textcontent);
		template.setHtmlcontent(htmlcontent);
		template.setServicename(servicename);
		template.setMethodname(methodname);

		return template;
	}

}
