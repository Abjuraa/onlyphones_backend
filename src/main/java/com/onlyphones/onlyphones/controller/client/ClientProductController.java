package com.onlyphones.onlyphones.controller.client;

import com.onlyphones.onlyphones.entity.Product;
import com.onlyphones.onlyphones.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client")

public class ClientProductController {

    private final ProductService productService;

    @GetMapping("/product")
    public ResponseEntity<?> getAllProducts() {
        List<Product> response = productService.getProducts();

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        Product response = productService.getProductById(id);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/latest")
    public ResponseEntity<List<Product>> getLatestProduct() {
        List<Product> response = productService.getLatestProduct();
        return ResponseEntity.ok(response);
    }
}
