package com.onlyphones.onlyphones.service;

import com.onlyphones.onlyphones.entity.Product;
import com.onlyphones.onlyphones.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProducts() {
        return (List<Product>) productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

}
