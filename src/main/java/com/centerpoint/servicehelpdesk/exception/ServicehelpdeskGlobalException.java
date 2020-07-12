package com.centerpoint.servicehelpdesk.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServicehelpdeskGlobalException {
	
	@ExceptionHandler(ServiceException.class)
	public final ResponseEntity<String> handleExceptions(ServiceException ex) {
		return new ResponseEntity<String>(ex.getErrmessage(), ex.getHttpStatus());
	}

}
