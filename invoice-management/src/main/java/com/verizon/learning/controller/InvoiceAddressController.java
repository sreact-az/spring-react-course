package com.verizon.learning.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.verizon.learning.valueobjects.CustomerAddress;
import com.verizon.learning.valueobjects.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class InvoiceAddressController {

	private WebClient webClient = WebClient.create("http://localhost:8080/");

	@GetMapping(path = "/inv-customer-postal-addresses")
	public Flux<CustomerAddress> getCustomerPostalAddress() {
		List<String> ids = Arrays.asList("101", "103", "105");
		return Flux.fromIterable(ids).flatMap(this::getPostalCustomerAddress);

	}

	@GetMapping(path = "/inv-customer-addresses")
	public Flux<CustomerAddress> getCustomerAddress() {
		List<String> ids = Arrays.asList("101", "103", "105");
		Flux<CustomerAddress> address1 = Flux.fromIterable(ids).flatMap(this::getPostalCustomerAddress);
		Flux<CustomerAddress> address2 = Flux.fromIterable(ids).flatMap(this::getDeliveryCustomerAddress);
		return Flux.merge(address1, address2);

	}

	private Flux<CustomerAddress> getPostalCustomerAddress(String id) {
		Flux<CustomerAddress> customers = webClient.get()
				.uri(uri -> uri.path("customers/{id}/address").queryParam("type", "P").build(id)).retrieve()
				.bodyToFlux(CustomerAddress.class);
		return customers;
	}

	private Flux<CustomerAddress> getDeliveryCustomerAddress(String id) {
		Flux<CustomerAddress> customers = webClient.get()
				.uri(uri -> uri.path("customers/{id}/address").queryParam("type", "D").build(id)).retrieve()
				.bodyToFlux(CustomerAddress.class);
		return customers;
	}

	@GetMapping(path = "/customer-with-address/{id}")
	public Mono<Customer> getCustomerWithAddress(@PathVariable String id) {
		Mono<Customer> monoCustomer = getCustomerAddresses(id);
		return monoCustomer;
	}
	@GetMapping(path = "/customer-with-addresses")
	public Flux<Customer> getCustomerWithAddress() {
		List<String> ids  = Arrays.asList("101", "103", "105");
		return Flux.fromIterable(ids).flatMap(this::getCustomerAddresses);
	}

	private Mono<Customer> getCustomerAddresses(String id) {
		Mono<Customer> monoCustomer = APIUtils.getCustomer(id).zipWhen(customer -> {
			Flux<CustomerAddress> postalAddress = Flux.merge(this.getPostalCustomerAddress(id),
					this.getDeliveryCustomerAddress(id));
			return postalAddress.collectList();
		}).map(x -> {
			Customer c = x.getT1();
			List<CustomerAddress> addresses = x.getT2();
			c.setAddresses(addresses);
			return c;
		});
		return monoCustomer;
	}
}
