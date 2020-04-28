package com.globomantics.globomanticsservices.repository;

import java.util.Optional;

import com.globomantics.globomanticsservices.models.Product;

public class ProductRepository {

	public Optional<Product> findById(int id) {
		Product product = new Product(0, "name", 1, 1); 
		return Optional.of(product); 
	}
}
