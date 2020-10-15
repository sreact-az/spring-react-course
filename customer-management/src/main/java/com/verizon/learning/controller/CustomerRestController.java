package com.verizon.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.verizon.learning.model.Customer;
import com.verizon.learning.model.CustomerDAO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@RestController
public class CustomerRestController {
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@GetMapping(path="/customers")
	public Flux<Customer> getCustomers(){
		Flux<Customer> customers = customerDAO.getAllCustomers().log();
		
		return customers;
	}
	
	@GetMapping(path="/customers/{id}")
	public Mono<ResponseEntity<Customer>> getCustomer(@PathVariable String id){
		
		//Mono<Customer> customer = customerDAO.getCustomerById(id);
		//To send Response code incase of empty data found
		Mono<ResponseEntity<Customer>> responseEntity =
				   customerDAO
				      .getCustomerById(id)
				      .map(x -> new ResponseEntity<>(x , HttpStatus.OK))
				      .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NO_CONTENT));
		return responseEntity;
		
		
		
	}
	

}
