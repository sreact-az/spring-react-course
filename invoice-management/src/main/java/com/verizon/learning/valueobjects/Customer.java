package com.verizon.learning.valueobjects;

import java.util.List;

public class Customer {

	private String id;
	private String name;
	
	List<CustomerAddress> addresses;

	public Customer() {

	}

	public Customer(String id, String name) {
		this.id = id;
		this.name = name;

	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {

		return this.id + " -- " + this.name;
	}

	public List<CustomerAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<CustomerAddress> addresses) {
		this.addresses = addresses;
	}
}
