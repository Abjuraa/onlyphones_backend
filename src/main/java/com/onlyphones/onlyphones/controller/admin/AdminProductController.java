package com.onlyphones.onlyphones.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlyphones.onlyphones.controller.ProductAbstractController;
import com.onlyphones.onlyphones.entity.Product;
import com.onlyphones.onlyphones.service.CloudinaryService;
import com.onlyphones.onlyphones.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@Slf4j

public class AdminProductController extends ProductAbstractController {

    private final ObjectMapper objectMapper;
    private final CloudinaryService cloudinaryService;
    private final ProductService productService;
    public static final String PAGE_DEFAULT = "0";
    public static final String SIZE_DEFAULT = "10";

    public AdminProductController(ProductService productService, ObjectMapper objectMapper, CloudinaryService cloudinaryService, ProductService productService1) {
        super(productService);
        this.objectMapper = objectMapper;
        this.cloudinaryService = cloudinaryService;
        this.productService = productService1;
    }

    // product/paginador?page=0&size=16
    @GetMapping("/product/paginador")
    public ResponseEntity<Map<String, Object>> pageProducts(
            @RequestParam(defaultValue = PAGE_DEFAULT) int page,
            @RequestParam(defaultValue = SIZE_DEFAULT) int size
    ) {
        int pageSize = Math.min(size, 24);
        Page<Product> product = productService.pagerProducts(page, pageSize);

        Map<String, Object> response = new HashMap<>();
        response.put("content", product.getContent());
        response.put("currentPage", product.getNumber());
        response.put("totalItems", product.getTotalElements());
        response.put("totalPages", product.getTotalPages());
        response.put("pageSize", product.getSize());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/createproduct")
    public ResponseEntity<Product> createProduct(
            @RequestPart("product") String productJson,
            @RequestPart("image")MultipartFile image) throws IOException {

        try {

            Product product = objectMapper.readValue(productJson, Product.class);
            Product saved = productService.createProductWithImage(product, image);
            return ResponseEntity.ok(saved);
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

    @PutMapping("/product/{id}/image")
    public Product updateImage(
            @PathVariable String id,
            @RequestParam("image") MultipartFile image
    ) {
        return productService.updateImage(id, image);
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
