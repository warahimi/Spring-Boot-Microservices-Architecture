package com.cougar.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(value = "product") // This annotation marks the class as a MongoDB document, mapping it to the "product" collection in MongoDB.
@AllArgsConstructor // This Lombok annotation generates a constructor with one parameter for each field in the class.
@NoArgsConstructor // This Lombok annotation generates a no-argument constructor.
@Builder // This Lombok annotation implements the builder pattern for the class, allowing for more readable and flexible object creation.
@Data // This Lombok annotation generates getters, setters, toString, equals, and hashCode methods.
public class Product {
    @Id // This annotation marks the field as the identifier for the MongoDB document.
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
