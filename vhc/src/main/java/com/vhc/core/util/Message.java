package com.vhc.core.util;

import java.io.Serializable;

/**
 * UI message object
 * 
 * @author KJC
 *
 */
public class Message implements Serializable {
	private static final long serialVersionUID = 493717426759324559L;
	public static final String SUCCESS = "alert alert-success";
	public static final String ERROR = "alert alert-danger";
	public static final String JSON_SUCCESS = "Success";
	public static final String JSON_ERROR = "Error";
	
	private String status;
	private String message;
	
	
	public Message() {
		this.status = "";
		this.message = "";
	}

	public Message(String status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

}
