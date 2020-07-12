package com.centerpoint.servicehelpdesk.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String errmessage;
	private HttpStatus httpStatus;
	
	public String getErrmessage() {
		return errmessage;
	}
	public void setErrmessage(String errmessage) {
		this.errmessage = errmessage;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public ServiceException(String errmessage, HttpStatus httpStatus) {
		this.errmessage = errmessage;
		this.httpStatus = httpStatus;
	}
}
