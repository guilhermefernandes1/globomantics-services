package com.globomantics.globomanticsservices.repository;

import java.util.List;

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

}
