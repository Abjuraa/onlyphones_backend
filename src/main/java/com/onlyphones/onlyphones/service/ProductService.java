package com.onlyphones.onlyphones.service;

import com.onlyphones.onlyphones.entity.Product;
import com.onlyphones.onlyphones.repository.CategoryRepository;
import com.onlyphones.onlyphones.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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

    public Page<Product> pagerProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product createProductWithImage(Product product, MultipartFile image) {
        Map upload = cloudinaryService.uploadFileWithMeta(image);

        product.setImage(upload.get("secure_url").toString());
        product.setImagePublicId(upload.get("public_id").toString());

        return productRepository.save(product);
    }

    public Product updateProduct(String id, Product newData) {
        return productRepository.findById(id)
                .map(existing -> {
                    existing.setModel(newData.getModel());
                    existing.setCapacity(newData.getCapacity());
                    existing.setColor(newData.getColor());
                    existing.setUnitsAvailable(newData.getUnitsAvailable());
                    existing.setPrice(newData.getPrice());
                    existing.setDiscount(newData.getDiscount());
                    existing.setHasDiscount(newData.getHasDiscount());
                    existing.setHasAvailable(newData.getHasAvailable());
                    existing.setBatteryPercentage(newData.getBatteryPercentage());
                    existing.setGrade(newData.getGrade());
                    existing.setWarranty(newData.getWarranty());
                    existing.setPhysicalState(newData.getPhysicalState());
                    existing.setHistory(newData.getHistory());
                    existing.setScreen(newData.getScreen());
                    existing.setProcessor(newData.getProcessor());
                    existing.setCamera(newData.getCamera());
                    existing.setSecurity(newData.getSecurity());
                    existing.setCategory(newData.getCategory());
                    return existing;
                })
                .map(productRepository::save)
                .orElseThrow(() -> new RuntimeException("producto no encontrado"));
    }

    public Product updateImage (String id, MultipartFile file) {

        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if(product.getImagePublicId() != null) {
            cloudinaryService.deleteFile(product.getImagePublicId());
        }

        Map upload = cloudinaryService.uploadFileWithMeta(file);

        product.setImage(upload.get("secure_url").toString());
        product.setImagePublicId(upload.get("public_id").toString());

        return productRepository.save(product);
    }


    public void deleteProduct(String id) {
       Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("el producto ha eliminar no se encontro"));

       if (product.getImagePublicId() != null){
           String url = product.getImagePublicId();
           cloudinaryService.deleteFile(url);
       }

       productRepository.delete(product);
    }



}
