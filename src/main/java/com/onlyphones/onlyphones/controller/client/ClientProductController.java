package com.onlyphones.onlyphones.controller.client;

import com.onlyphones.onlyphones.entity.Product;
import com.onlyphones.onlyphones.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client")

public class ClientProductController {

    final String PAGE = "0";
    final String SIZE = "16";
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

    // product/paginador?page=0&size=16
    @GetMapping("/product/paginador")
    public ResponseEntity<?> pagerProducts(
            @RequestParam(defaultValue = PAGE) int page,
            @RequestParam(defaultValue = SIZE) int size
    ) {
        int minSize = Math.min(size, 24);
        Page<Product> product = productService.pagerProducts(page, minSize);

        Map<String, Object> response = new HashMap<>();
        response.put("content", product.getContent());
        response.put("currentPage", product.getNumber());
        response.put("totalItems", product.getTotalElements());
        response.put("totalPages", product.getTotalPages());
        response.put("pageSize", product.getSize());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/latest")
    public ResponseEntity<List<Product>> getLatestProduct() {
        List<Product> response = productService.getLatestProduct();
        return ResponseEntity.ok(response);
    }
}
