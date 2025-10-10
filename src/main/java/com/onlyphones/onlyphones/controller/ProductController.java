package com.onlyphones.onlyphones.controller;

import com.onlyphones.onlyphones.entity.Product;
import com.onlyphones.onlyphones.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor

public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getallproducts")
    public ResponseEntity<?> getAllProducts(){
        List<Product> response = productService.getProducts();

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/createproduct")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        Product response = productService.createProduct(product);

        if (response == null) {
            ResponseEntity.badRequest().body("No se pudo crear el producto");
        }

        return ResponseEntity.ok(response);
    }
}
