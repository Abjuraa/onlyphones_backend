package com.onlyphones.onlyphones.controller.client;

import com.onlyphones.onlyphones.controller.ProductAbstractController;
import com.onlyphones.onlyphones.entity.Product;
import com.onlyphones.onlyphones.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/client")

public class ClientProductController extends ProductAbstractController {

    private final ProductService productService;

    public ClientProductController(ProductService productService, ProductService productService1) {
        super(productService);
        this.productService = productService1;
    }


    @GetMapping("/product/latest")
    public ResponseEntity<List<Product>> getLatestProduct() {
        List<Product> response = productService.getLatestProduct();
        return ResponseEntity.ok(response);
    }
}
