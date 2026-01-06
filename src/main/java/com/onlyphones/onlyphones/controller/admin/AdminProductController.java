package com.onlyphones.onlyphones.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlyphones.onlyphones.controller.ProductAbstractController;
import com.onlyphones.onlyphones.entity.Product;
import com.onlyphones.onlyphones.service.CloudinaryService;
import com.onlyphones.onlyphones.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/admin")
@Slf4j

public class AdminProductController extends ProductAbstractController {

    private final ObjectMapper objectMapper;
    private final CloudinaryService cloudinaryService;
    private final ProductService productService;

    public AdminProductController(ProductService productService, ObjectMapper objectMapper, CloudinaryService cloudinaryService, ProductService productService1) {
        super(productService);
        this.objectMapper = objectMapper;
        this.cloudinaryService = cloudinaryService;
        this.productService = productService1;
    }


    @PostMapping("/createproduct")
    public ResponseEntity<Product> createProduct(
            @RequestPart("product") String productJson,
            @RequestPart("image")MultipartFile image) throws IOException {

        try {

            Product product = objectMapper.readValue(productJson, Product.class);
            String url = cloudinaryService.uploadFile(image);
            product.setImage(url);
            Product response = productService.createProduct(product);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/updateproduct/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product newData) {
        Product response = productService.updateProduct(id, newData);

        if (response == null) {
           return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteproduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("producto eliminado correctamente");
        } catch(Exception e){
            return ResponseEntity.status(500).body("Error al eliminar el producto" + e.getMessage());
        }
    }
}
