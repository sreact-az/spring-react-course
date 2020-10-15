package com.verizon.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.verizon.learning.model.CustomerAddress;
import com.verizon.learning.model.CustomerAddressRepository;

import reactor.core.publisher.Flux;
@RestController
public class CustomerAddressRestcontroller {

	@Autowired
	private CustomerAddressRepository customerAddressRepository;
	
	@GetMapping(path="/customers/{id}/address")
	public Flux<CustomerAddress> getAddressByCustomerId(@PathVariable String id){
		return customerAddressRepository.findBycustomerId(id);
	}
	
	@GetMapping(path="/customers/{id}/address", params = {"type"})
	public Flux<CustomerAddress> getAddressByCustomerIdAndType(@PathVariable String id, @RequestParam String type){
		return customerAddressRepository.findByCustomerIdAndType(id, type);
	}
}
