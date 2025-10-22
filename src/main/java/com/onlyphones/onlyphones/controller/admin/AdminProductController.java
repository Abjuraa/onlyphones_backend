package com.onlyphones.onlyphones.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlyphones.onlyphones.entity.Product;
import com.onlyphones.onlyphones.service.CloudinaryService;
import com.onlyphones.onlyphones.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j

public class AdminProductController {

    private final ObjectMapper objectMapper;
    private final CloudinaryService cloudinaryService;
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
    public ResponseEntity<?> createProduct(
            @RequestPart("product") String productJson,
            @RequestPart("image")MultipartFile image) throws IOException {

        try {

            Product product = objectMapper.readValue(productJson, Product.class);
            String url = cloudinaryService.uploadFile(image);
            product.setImage(url);
            Product response = productService.createProduct(product);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al crear el producto: " + e.getMessage());
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
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("producto eliminado correctamente");
        } catch(Exception e){
            return ResponseEntity.status(500).body("Error al eliminar el producto" + e.getMessage());
        }
    }
}
