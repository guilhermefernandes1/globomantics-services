package com.globomantics.globomanticsservices.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.globomantics.globomanticsservices.models.Product;
import com.globomantics.globomanticsservices.services.ProductService;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ProductController {
	
	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<?> getProduct(@PathVariable Integer id){
		
		return productService.findById(id)
                .map(product -> {
                    try {
                        return ResponseEntity
                                .ok()
                                .eTag(Integer.toString(product.getVersion()))
                                .location(new URI("/product/" + product.getId()))
                                .body(product);
                    } catch (URISyntaxException e ) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                })
                .orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/product")
	public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
		
		Product newProduct = productService.save(product);
		
		try {
			return ResponseEntity
					.created(new URI("/product/" + newProduct.getId()))
					.eTag(Integer.toString(newProduct.getVersion()))
					.body(newProduct);
		} catch (URISyntaxException e) {			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
