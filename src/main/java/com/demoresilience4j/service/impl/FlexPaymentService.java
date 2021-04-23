package com.demoresilience4j.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.demoresilience4j.service.PaymentService;
import com.demoresilience4j.service.dto.PaymentRequest;
import com.demoresilience4j.service.exception.PaymentException;

@Service
public class FlexPaymentService implements PaymentService {
	
	private boolean ok = true;
	private int count = 0;
	
	@Override
	public String pay(PaymentRequest request) throws PaymentException  {
		count++;
		if(ok) {
			try {
				if(count % 2 == 0) {
					TimeUnit.SECONDS.sleep(2);
					System.out.println(String.format("Payment done (delayed): account {%s}, amount {%s}", request.getAccount(), request.getAmount()));
					throw new PaymentException("Fail delayed result...");
				} else {
					System.out.println(String.format("Payment done: account {%s}, amount {%s}", request.getAccount(), request.getAmount()));
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "Ok";
		} else {
			throw new PaymentException("Fail getting result...");
		}
	}

	@Override
	public boolean change() {
		return ok = !ok;
	}
}
