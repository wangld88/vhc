package com.vhc.core.exception;

public class DataAccessException extends RuntimeException {

	static final long serialVersionUID = 0;
	
	public DataAccessException(Exception e){
		super(e);
	}
}
