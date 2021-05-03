package com.demoresilience4j.service.exception;

public class PaymentIgnoredException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PaymentIgnoredException(String string) {
		super(string);
	}
}
