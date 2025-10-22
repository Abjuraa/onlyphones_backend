package com.onlyphones.onlyphones.service;

import com.onlyphones.onlyphones.entity.Product;
import com.onlyphones.onlyphones.repository.CategoryRepository;
import com.onlyphones.onlyphones.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class ProductService {

    private final CloudinaryService cloudinaryService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> getProducts() {
        return (List<Product>) productRepository.findAll();
    }

    public List<Product> getLatestProduct() {
        return productRepository.findTop10ByOrderByCreatedAtDesc();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(String id, Product newData) {
        return productRepository.findById(id)
                .map(existing -> {
                    existing.setCategory(newData.getCategory());
                    existing.setCapacity(newData.getCapacity());
                    existing.setColor(newData.getColor());
                    existing.setModel(newData.getModel());
                    existing.setPrice(newData.getPrice());
                    existing.setDiscount(newData.getDiscount());
                    return existing;
                })
                .map(productRepository::save)
                .orElseThrow(() -> new RuntimeException("producto no encontrado"));
    }

    public String extractIdPublic (String url) {
        if (url == null || !url.contains("/upload/")) {
            return null;
        }

        String[] ulrPart = url.split("/upload/");
        String path = ulrPart[1];

        if (path.startsWith("v")) {
            int firstSlash = path.indexOf("/");
            path = path.substring(firstSlash + 1);
        }

        return path.substring(0, path.lastIndexOf('.'));
    }

    public void deleteProduct(String id) {
       Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("el producto ha eliminar no se encontro"));

       if (product.getImage() != null) {
           String publicId = extractIdPublic(product.getImage());

           if (publicId != null) {
               cloudinaryService.deleteFile(publicId);
           }
       }

       productRepository.delete(product);
    }



}
