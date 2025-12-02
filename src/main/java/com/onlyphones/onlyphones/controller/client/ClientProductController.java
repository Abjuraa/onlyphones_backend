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

    public static final String PAGE_DEFAULT = "0";
    public static final String SIZE_DEFAULT = "16";
    private final ProductService productService;

    public ClientProductController(ProductService productService, ProductService productService1) {
        super(productService);
        this.productService = productService1;
    }


    // product/paginador?page=0&size=16
    @GetMapping("/product/paginador")
    public ResponseEntity<Product> pageProducts(
            @RequestParam(defaultValue = PAGE_DEFAULT) String page,
            @RequestParam(defaultValue = SIZE_DEFAULT) String size
    ) {
        int minSize = Math.min(Integer.getInteger(size), 24);
        Page<Product> product = productService.pagerProducts(Integer.getInteger(page), minSize);

        Map<String, Object> response = new HashMap<>();
        response.put("content", product.getContent());
        response.put("currentPage", product.getNumber());
        response.put("totalItems", product.getTotalElements());
        response.put("totalPages", product.getTotalPages());
        response.put("pageSize", product.getSize());

        return ResponseEntity.ok((Product) response);
    }

    @GetMapping("/product/latest")
    public ResponseEntity<List<Product>> getLatestProduct() {
        List<Product> response = productService.getLatestProduct();
        return ResponseEntity.ok(response);
    }
}
