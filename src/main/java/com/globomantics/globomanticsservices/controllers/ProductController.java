package com.globomantics.globomanticsservices.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.globomantics.globomanticsservices.models.Product;
import com.globomantics.globomanticsservices.services.ProductService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
public class ProductController {
	
	private static final Logger logger = LogManager.getLogger(ProductController.class);
	
	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<?> getProduct(@PathVariable Integer id) {
		
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
	
	@PutMapping("/product/{id}")
	public ResponseEntity<?> updateProduct(@RequestBody Product product,
												 @PathVariable Integer id,
												 @RequestHeader("If-Match") Integer ifMatch) {
		
		Optional<Product> existingProduct = productService.findById(id);
		
		return existingProduct.map(p -> {
			if(!(p.getVersion() == ifMatch)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
			
			// Update product
			p.setName(product.getName());
			p.setQuantity(p.getQuantity());
			p.setVersion(p.getVersion() + 1);
			
			try {
                // Update the product and return an ok response
                if (productService.update(p)) {
                    return ResponseEntity.ok()
                            .location(new URI("/product/" + p.getId()))
                            .eTag(Integer.toString(p.getVersion()))
                            .body(p);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (URISyntaxException e) {
                // An error occurred trying to create the location URI, return an error
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
		}).orElse(ResponseEntity.notFound().build());
		
	}
	
	@DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {

        logger.info("Deleting product with ID {}", id);

        // Get the existing product
        Optional<Product> existingProduct = productService.findById(id);

        return existingProduct.map(p -> {
            if (productService.delete(p.getId())) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }).orElse(ResponseEntity.notFound().build());
    }
}
