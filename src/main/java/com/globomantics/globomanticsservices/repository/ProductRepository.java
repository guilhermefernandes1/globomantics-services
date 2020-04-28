package com.globomantics.globomanticsservices.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.globomantics.globomanticsservices.models.Product;

public class ProductRepository {

	public Optional<Product> findById(int id) {
		Product product = new Product(0, "name", 1, 1); 
		return Optional.of(product); 
	}
	
	public List<Product> findAll() {
		List<Product> products = new ArrayList<>();
		
		return products;
	}
}
