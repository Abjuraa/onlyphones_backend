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

    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(String id, Product newData) {
        return productRepository.findById(id)
                .map(existing -> {
                    existing.setCategory(newData.getCategory());
                    existing.setCapacity(newData.getCapacity());
                    existing.setColor(newData.getColor());
                    existing.setModel(newData.getModel());
                    existing.setPrice(newData.getPrice());
                    existing.setDiscount(newData.getDiscount());
                    return existing;
                })
                .map(productRepository::save)
                .orElseThrow(() -> new RuntimeException("producto no encontrado"));
    }

    public boolean deleteProduct(String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }

        return false;
    }

}
