package com.onlyphones.onlyphones.controller.admin;

import com.onlyphones.onlyphones.entity.Product;
import com.onlyphones.onlyphones.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j

public class AdminProductController {

    private final ProductService productService;

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> response = productService.getProducts();

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        Product response = productService.getProductById(id);

        if(response == null) {
            return ResponseEntity.notFound().build();
        }

        log.info("Producto traido por el id {}: {} ",id, response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/createproduct")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        try{
            Product response = productService.createProduct(product);

            if (response == null) {
                throw new RuntimeException("No se puede crear el producto");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al intentar crear el producto");
        }
    }

    @PutMapping("/updateproduct/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody Product newData) {
        Product response = productService.updateProduct(id, newData);

        if (response == null) {
           return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteproduct/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        boolean response = productService.deleteProduct(id);

        if (response) {
            log.info("producto eliminado corectamente con el id: {}", id);
            return ResponseEntity.ok(id);
        }

        return ResponseEntity.notFound().build();
    }
}
