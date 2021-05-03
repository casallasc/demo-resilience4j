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
import io.vavr.control.Try;


@RestController
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	private final CircuitBreaker circuitBreaker;
	
	public PaymentController(@Autowired CircuitBreakerRegistry circuitBreakerRegistry) {
		 this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("flexPayment");
	}
	
	@GetMapping("/payment")
	public String pay(@RequestParam("account") String account, @RequestParam("amount") String amount) throws Throwable {
		return Try.of(() -> Decorators.ofCheckedFunction(payFn())
				.withCircuitBreaker(circuitBreaker)
				.apply(new PaymentRequest(account, amount)))
				.onFailure(ex -> log(ex))
				.getOrElse("Fail!");
	}
	
	@GetMapping("/payFailed")
	public String payFailed(@RequestParam("account") String account, @RequestParam("amount") String amount) throws Throwable {
		return Try.of(() -> Decorators.ofCheckedFunction(payFailedFn())
				.withCircuitBreaker(circuitBreaker)
				.apply(new PaymentRequest(account, amount)))
				.onFailure(ex -> log(ex))
				.getOrElse("Fail!");
	}
	
	private CheckedFunction1<PaymentRequest, String> payFn() {
        return req -> paymentService.pay(req);
    }
	
	private CheckedFunction1<PaymentRequest, String> payFailedFn() {
        return req -> paymentService.payFailed(req);
    }
	
	@GetMapping("/change")
	public String change() throws Throwable {
		return paymentService.change();
	}

	private void log(Throwable t) {
		System.out.println(t.getClass().getName() + ": " + t.getMessage());
	}
}
