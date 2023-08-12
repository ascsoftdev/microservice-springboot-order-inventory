package com.asc.productservice.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.asc.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{
	
}
