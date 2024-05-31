package com.cougar.productservice.unittests;

import com.cougar.productservice.dto.ProductRequest;
import com.cougar.productservice.dto.ProductResponse;
import com.cougar.productservice.model.Product;
import com.cougar.productservice.repository.ProductRepository;
import com.cougar.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceUnitTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        // Initialize mocks before each test
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct() {
        // Create a sample ProductRequest
        ProductRequest productRequest = ProductRequest.builder()
                .name("Test Product")
                .description("Test Description")
                .price(BigDecimal.valueOf(100.0))
                .build();

        // Create a sample Product to return when saving
        Product savedProduct = Product.builder()
                .id("1")
                .name("Test Product")
                .description("Test Description")
                .price(BigDecimal.valueOf(100.0))
                .build();

        // Mock the save method of the repository to return the saved product
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // Call the createProduct method
        productService.createProduct(productRequest);

        // Verify that the save method was called once
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void testGetAllProducts() {
        // Create a list of sample products to return
        Product product1 = Product.builder()
                .id("1")
                .name("Product 1")
                .description("Description 1")
                .price(BigDecimal.valueOf(50.0))
                .build();

        Product product2 = Product.builder()
                .id("2")
                .name("Product 2")
                .description("Description 2")
                .price(BigDecimal.valueOf(100.0))
                .build();

        List<Product> productList = Arrays.asList(product1, product2);

        // Mock the findAll method of the repository to return the sample list
        when(productRepository.findAll()).thenReturn(productList);

        // Call the getAllProducts method
        List<ProductResponse> productResponses = productService.getAllProducts();

        // Verify that the findAll method was called once
        verify(productRepository, times(1)).findAll();

        // Verify the results
        assertEquals(2, productResponses.size());
        assertEquals("Product 1", productResponses.get(0).getName());
        assertEquals("Product 2", productResponses.get(1).getName());
    }
}
