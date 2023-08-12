package com.asc.productservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.asc.productservice.dto.ProductRequest;
import com.asc.productservice.dto.ProductResponse;
import com.asc.productservice.model.Product;
import com.asc.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

	private final ProductRepository productRepository;
	
	public void createProducts(ProductRequest productRequest) {
		
		Product p = Product.builder()
				.name(productRequest.getName())
				.description(productRequest.getDescription())
				.price(productRequest.getPrice())
				.build();
		
		productRepository.save(p);
		log.info("Product {} is Created ", p.getId());
	}
	
	public List<ProductResponse> getAllProducts() {
		List<Product> products = productRepository.findAll();
		List<ProductResponse> productResponse = products.stream().map(this::mapToProductResponse).toList();
		return productResponse;
	}
	
	public ProductResponse mapToProductResponse(Product product) {
		return ProductResponse.builder().id(product.getId())
		.name(product.getName())
		.description(product.getDescription())
		.price(product.getPrice())
		.build();
	}
}
