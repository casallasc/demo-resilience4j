package com.demoresilience4j.service;

import com.demoresilience4j.service.dto.PaymentRequest;
import com.demoresilience4j.service.exception.PaymentException;

public interface PaymentService {

	String pay(PaymentRequest request) throws PaymentException;
}
