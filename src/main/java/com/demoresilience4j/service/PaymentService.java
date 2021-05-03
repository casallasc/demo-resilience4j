package com.demoresilience4j.service;

import com.demoresilience4j.service.dto.PaymentRequest;

public interface PaymentService {

	String pay(PaymentRequest request) throws Exception;
	String payFailed(PaymentRequest request) throws Exception;
	String change();
}
