package com.globomantics.globomanticsservices.models;

public class Product {

	private int id;
	private String name;
	private int quantity;
	private int version;
	
	public Product() {
		
	}
	
	public Product(int id, String name, int quantity, int version) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.version = version;
	}
	
	public Product(String name, int quantity) {
		this.name = name;
		this.quantity = quantity;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public int getId() {
		return id;
	}	
}
