package com.demoresilience4j.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.demoresilience4j.service.PaymentService;
import com.demoresilience4j.service.dto.PaymentRequest;
import com.demoresilience4j.service.exception.PaymentException;
import com.demoresilience4j.service.exception.PaymentIgnoredException;

@Service
public class FlexPaymentService implements PaymentService {
	
	private boolean ok = true;
	private int count = 0;
	
	@Override
	public String pay(PaymentRequest request) throws Exception  {
		count++;
		if(ok) {
				if(count % 2 == 0) {
					TimeUnit.SECONDS.sleep(2);
					System.out.println(String.format("Payment done (delayed): account {%s}, amount {%s}",
							request.getAccount(), request.getAmount()));
				} else {
					System.out.println(String.format("Payment done: account {%s}, amount {%s}",
							request.getAccount(), request.getAmount()));
				}
			return "Ok";
		} else {
			throw new PaymentException("Fail getting result from service...");
		}
	}
	
	@Override
	public String payFailed(PaymentRequest request) throws Exception  {

		throw new PaymentIgnoredException("Fail getting result from service...");
	}

	@Override
	public String change() {
		ok = !ok;
		if(ok)
			return "Payment service ok";
		else
			return "Payment service failing";
	}
}
