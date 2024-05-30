package com.cougar.productservice.integrationtests;

import com.cougar.productservice.dto.ProductRequest;
import com.cougar.productservice.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class ProductServiceIntegrationTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // converts POJO object to JSON and JSON to POJO

    @Autowired
    ProductRepository productRepository;
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry)
    {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    // Creating Integration tests

    /*
        Integration test for ProductController -> createProduct() end point
        we send a ProductResponse object and expect the response status to be CREATED
    */
    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequest productRequest = getProductRequest();
        String productRequestString = objectMapper.writeValueAsString(productRequest);
        // to make a request from integration test we use mock nvc object which provide mock servlete environment where we can call the endpoints
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequestString))
                .andExpect(status().isCreated());

        // verify the insertion
        Assertions.assertEquals(1, productRepository.findAll().size());
        Assertions.assertEquals("iphone 12", productRepository.findByName("iphone 12").getName());
        Assertions.assertEquals("Promax", productRepository.findByName("iphone 12").getDescription());
        Assertions.assertEquals(BigDecimal.valueOf(1200), productRepository.findByName("iphone 12").getPrice());
    }
    // getProduct end point test

    @Test
    void shouldGetAllProducts() throws Exception {
        // First, create a product
        ProductRequest productRequest = getProductRequest();
        String productRequestString = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestString))
                .andExpect(status().isCreated());

        // Then, retrieve all products and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("iphone 12"))
                .andExpect(jsonPath("$[0].description").value("Promax"))
                .andExpect(jsonPath("$[0].price").value(1200));
    }
    private ProductRequest getProductRequest() {
        return ProductRequest.builder()
                .name("iphone 12")
                .description("Promax")
                .price(BigDecimal.valueOf(1200))
                .build();
    }

}
