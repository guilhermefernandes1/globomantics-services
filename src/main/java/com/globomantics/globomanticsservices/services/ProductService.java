package com.globomantics.globomanticsservices.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.globomantics.globomanticsservices.models.Product;

@Service
public class ProductService {

	public Optional<Product> findById(Integer id) {
		Product product = new Product(0, "name", 1, 1); 
		return Optional.of(product);
	}
	
	public Product save(Product product) {
		return null;
	}
}
