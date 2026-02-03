package com.onlyphones.onlyphones.repository;

import com.onlyphones.onlyphones.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, String> {
    List<Product> findTop10ByHasAvailableIsTrueOrderByCreatedAtDesc();
    Page<Product> findAll(Pageable pageable);
    List<Product> findAllfindAllByHasAvailableIsTrue();
}
