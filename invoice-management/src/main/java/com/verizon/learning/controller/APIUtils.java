package com.verizon.learning.controller;

import org.springframework.web.reactive.function.client.WebClient;

import com.verizon.learning.valueobjects.Customer;

import reactor.core.publisher.Mono;

public class APIUtils {
	private static WebClient webClient = WebClient.create("http://localhost:8080/");
	public static Mono<Customer> getCustomer(String customerId) {
		return webClient.get().uri("/customers/{customerId}",customerId).retrieve().bodyToMono(Customer.class);
	}
}
