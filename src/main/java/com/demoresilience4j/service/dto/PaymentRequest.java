package com.demoresilience4j.service.dto;

public class PaymentRequest {
	
	private String account;
	private String amount;
	public PaymentRequest(String account, String amount) {
		super();
		this.account = account;
		this.amount = amount;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	

}
