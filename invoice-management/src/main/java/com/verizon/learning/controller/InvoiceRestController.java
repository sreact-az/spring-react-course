package com.verizon.learning.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.verizon.learning.valueobjects.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class InvoiceRestController {

	private WebClient webClient = WebClient.create("http://localhost:8080/");
	
	@GetMapping(path="/invoice-customers")
	public Flux<Customer>  handleInvoiceCustomer() {
		return webClient.get().uri("/customers").retrieve().bodyToFlux(Customer.class);
	}
	
	@GetMapping(path="/invoice-customers", params = {"customerId"})
	public Mono<Customer>  handleInvoiceCustomerByCustomerIds(@RequestParam String customerId) {
		return webClient.get().uri("/customers/{customerId}",customerId).retrieve().bodyToMono(Customer.class);
	}
	
	@GetMapping(path="/invoice-customers/special")
	public Flux<Customer>  handleInvoiceCustomerByCustomerIds() {
		List<String> ids = Arrays.asList("101","103","105");
		return Flux.fromIterable(ids).flatMap(this::getCustomerById);
	}

	private Mono<Customer> getCustomerById(String id) {
		Mono<Customer> customers = webClient.get().uri("/customers/{customerId}",id).retrieve().bodyToMono(Customer.class);
		return customers;
	}
}
