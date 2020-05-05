package com.globomantics.globomanticsservices.repository;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import com.globomantics.globomanticsservices.models.Product;

@ExtendWith({ DBUnitExtension.class, SpringExtension.class })
@SpringBootTest
@ActiveProfiles("test")
public class ProductRepositoryTest {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private ProductRepository repository;
	
	public ConnectionHolder getConnectionHolder() {
        // Return a function that retrieves a connection from our data source
        return () -> dataSource.getConnection();
    }
	
	@Test
    @DataSet("products.yml")
    void testFindAll() {
        List<Product> products = repository.findAll();
        Assertions.assertEquals(2, products.size(), "We should have 2 products in our database");
    }
	
	@Test
    @DataSet("products.yml")
    void testFindByIdSuccess() {
        // Find the product with ID 2
        Optional<Product> product = repository.findById(2);

        // Validate that we found it
        Assertions.assertTrue(product.isPresent(), "Product with ID 2 should be found");

        // Validate the product values
        Product p = product.get();
        Assertions.assertEquals(2, p.getId(), "Product ID should be 2");
        Assertions.assertEquals("Product 2", p.getName(), "Product name should be \"Product 2\"");
        Assertions.assertEquals(5, p.getQuantity(), "Product quantity should be 5");
        Assertions.assertEquals(2, p.getVersion(), "Product version should be 2");
    }
	
	@Test
    @DataSet("products.yml")
    void testFindByIdNotFound() {
        // Find the product with ID 2
        Optional<Product> product = repository.findById(3);

        // Validate that we found it
        Assertions.assertFalse(product.isPresent(), "Product with ID 3 should be not be found");
    }
	
    @Test
    @DataSet(value = "products.yml")
    void testSave() {
        // Create a new product and save it to the database
        Product product = new Product("Product 5", 5);
        product.setVersion(1);
        Product savedProduct = repository.save(product);

        // Validate the saved product
        Assertions.assertEquals("Product 5", savedProduct.getName());
        Assertions.assertEquals(5, savedProduct.getQuantity());

        // Validate that we can get it back out of the database
        Optional<Product> loadedProduct = repository.findById(savedProduct.getId());
        Assertions.assertTrue(loadedProduct.isPresent(), "Could not reload product from the database");
        Assertions.assertEquals("Product 5", loadedProduct.get().getName(), "Product name does not match");
        Assertions.assertEquals(5, loadedProduct.get().getQuantity(), "Product quantity does not match");
        Assertions.assertEquals(1, loadedProduct.get().getVersion(), "Product version is incorrect");
    }
    
}
