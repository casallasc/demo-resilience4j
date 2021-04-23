package com.demoresilience4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demoresilience4j.service.PaymentService;
import com.demoresilience4j.service.dto.PaymentRequest;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.decorators.Decorators;
import io.vavr.CheckedFunction1;


@RestController
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	private final CircuitBreaker circuitBreaker;
	
	public PaymentController(@Autowired CircuitBreakerRegistry circuitBreakerRegistry) {
		 this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("flexPayment");
		 System.out.println(this.circuitBreaker.getCircuitBreakerConfig());
	}
	
	@GetMapping("/payment")
	public String pay(@RequestParam("account") String account, @RequestParam("amount") String amount) throws Throwable {
		return Decorators.ofCheckedFunction(payFn())
        .withCircuitBreaker(circuitBreaker)
        .apply(new PaymentRequest(account, amount));
	}
	
	private CheckedFunction1<PaymentRequest, String> payFn() {
        return req -> paymentService.pay(req);
    }
	
	@GetMapping("/change")
	public boolean change() throws Throwable {
		return paymentService.change();
	}
}
