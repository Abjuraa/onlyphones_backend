package com.onlyphones.onlyphones.repository;

import com.onlyphones.onlyphones.entity.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface CategoryRepository extends CrudRepository<Category, String> {
    Optional<Category> findByNameCategory(String nameCategory);
}
