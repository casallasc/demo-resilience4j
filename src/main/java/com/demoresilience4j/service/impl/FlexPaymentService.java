package com.demoresilience4j.service.impl;

import org.springframework.stereotype.Service;

import com.demoresilience4j.service.PaymentService;
import com.demoresilience4j.service.dto.PaymentRequest;
import com.demoresilience4j.service.exception.PaymentException;

@Service
public class FlexPaymentService implements PaymentService {
	
	private boolean ok;
	private int count;
	
	@Override
	public String pay(PaymentRequest request) throws PaymentException  {
		
		if(count % 5 == 0) {
			ok = !ok;
		}
		
		count++;
		
		if(ok) {
			System.out.println(String.format("Payment done: account {%s}, amount {%s}", request.getAccount(), request.getAccount()));
			return "Ok";
		} else {
			throw new PaymentException("Fail getting result...");
		}
	}
}
