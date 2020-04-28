package com.globomantics.globomanticsservices.services;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.globomantics.globomanticsservices.models.Product;
import com.globomantics.globomanticsservices.repository.ProductRepository;

@Service
public class ProductService {
	
	private static final Logger logger = LogManager.getLogger(ProductService.class);
	
	private final ProductRepository productRepository;	

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Optional<Product> findById(Integer id) {
		logger.info("Find product with id: {}", id);
        return productRepository.findById(id);
	}
	
    public List<Product> findAll() {
        logger.info("Find all products");
        return productRepository.findAll();
    }
	
	public Product save(Product product) {
		return null;
	}
	
	public boolean update(Product product) {
		return true;
	}
	
	public boolean delete(Integer id) {
		return true;
	}
}
