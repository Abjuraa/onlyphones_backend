package com.onlyphones.onlyphones.repository;

import com.onlyphones.onlyphones.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.web.PagedModel;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, String> {
    List<Product> findTop10ByOrderByCreatedAtDesc();
    Page<Product> findAll(Pageable pageable);
}
