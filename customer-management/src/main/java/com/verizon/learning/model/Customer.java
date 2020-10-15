package com.verizon.learning.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Customer {
	@Id
	private String id;
	private String name;

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

}
