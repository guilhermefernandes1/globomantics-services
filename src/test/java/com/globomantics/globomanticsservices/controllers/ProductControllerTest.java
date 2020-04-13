package com.globomantics.globomanticsservices.controllers;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.globomantics.globomanticsservices.models.Product;
import com.globomantics.globomanticsservices.services.ProductService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

	@MockBean
	private ProductService service;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("GET /product/1 - Found")
	void testGetProductByIdFound() throws Exception {
		// Setup mocked service
		Product mockProduct = new Product(1, "Product Name", 10, 1);
		doReturn(Optional.of(mockProduct)).when(service).findById(1);
		
		// Execute the GET request		
		mockMvc.perform(get("/product/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			
			.andExpect(header().string(HttpHeaders.ETAG, "\"1\""))
			.andExpect(header().string(HttpHeaders.LOCATION, "/product/1"))
			
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.name", is("Product Name")))
            .andExpect(jsonPath("$.quantity", is(10)))
            .andExpect(jsonPath("$.version", is(1)));
	}
	
	@Test
	@DisplayName("GET /product/1 - Not Found")
	void testGetProductByIdNotFound() throws Exception {
		doReturn(Optional.empty()).when(service).findById(1);
		
		mockMvc.perform(get("/product/{id}", 1))
			.andExpect(status().isNotFound());
		
	}
}
