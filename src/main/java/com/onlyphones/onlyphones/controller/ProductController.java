package com.onlyphones.onlyphones.controller;

import com.onlyphones.onlyphones.entity.Product;
import com.onlyphones.onlyphones.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j

public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getallproducts")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> response = productService.getProducts();

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("getproductbyid/{id}")
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
        Product response = productService.createProduct(product);

        if (response == null) {
            ResponseEntity.badRequest().body("No se pudo crear el producto");
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping("updateproduct/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody Product newData) {
        Product response = productService.updateProduct(id, newData);

        if (response == null) {
           return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("deleteproduct/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        boolean response = productService.deleteProduct(id);

        if (response) {
            log.info("producto eliminado corectamente con el id: {}", id);
            return ResponseEntity.ok(id);
        }

        return ResponseEntity.notFound().build();
    }
}
