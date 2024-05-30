package com.cougar.productservice.controller;

import com.cougar.productservice.dto.ProductRequest;
import com.cougar.productservice.dto.ProductResponse;
import com.cougar.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/test")
    public String hello()
    {
        return "Hello";
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest)
    {
        System.out.println("Controller: " + productRequest);
        productService.createProduct(productRequest);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProductResponse> getAllProducts()
    {
        return productService.getAllProducts();
    }
}
