package com.cougar.productservice.repository;

import com.cougar.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface ProductRepository extends MongoRepository<Product, String> {
    Product findByName(String s);
}
