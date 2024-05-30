package com.cougar.productservice.service;

import com.cougar.productservice.dto.ProductRequest;
import com.cougar.productservice.dto.ProductResponse;
import com.cougar.productservice.model.Product;
import com.cougar.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // for constructor injection
@Slf4j
public class ProductService {


    private final ProductRepository productRepository;
    public void createProduct(ProductRequest productRequest) {
        // create the product object using build method
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        // save the product to database using repository
        System.out.println("Service product:" + product );
        productRepository.save(product);
        log.info("Product {} is saved successfully", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapProductToProductResponse).toList();
    }

    private ProductResponse mapProductToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
