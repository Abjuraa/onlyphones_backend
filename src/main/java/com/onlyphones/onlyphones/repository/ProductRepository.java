package com.onlyphones.onlyphones.repository;

import com.onlyphones.onlyphones.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, String> {

    List<Product> findTop10ByOrderByCreatedAtDesc();
}
