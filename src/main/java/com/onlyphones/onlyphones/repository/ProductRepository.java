package com.onlyphones.onlyphones.repository;

import com.onlyphones.onlyphones.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, String> {

}
