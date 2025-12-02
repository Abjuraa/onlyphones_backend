package com.onlyphones.onlyphones.service;

import com.onlyphones.onlyphones.entity.Category;
import com.onlyphones.onlyphones.exceptions.category_exceptions.CategoryAlredyExistException;
import com.onlyphones.onlyphones.exceptions.category_exceptions.CategoryErrorUpdateException;
import com.onlyphones.onlyphones.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return (List<Category>) categoryRepository.findAll();
    }

    public Category getCategoryById(String id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category createCategory(Category newCategory) {
        Optional<Category> existingCategory = categoryRepository.findByNameCategory(newCategory.getNameCategory());

        if (existingCategory.isPresent()) {
            throw new CategoryAlredyExistException("Categoria ya existente");
        }
        return categoryRepository.save(newCategory);
    }

    public Category updateCategory(String id, Category editCategory) {
        return categoryRepository.findById(id)
                .map(existing -> {
                    existing.setNameCategory(editCategory.getNameCategory());
                    existing.setProducts(editCategory.getProducts());
                    return existing;
                })
                .map(categoryRepository::save)
                .orElseThrow(() -> new CategoryErrorUpdateException("error al editar categoria"));
    }

    public void deleteCategory(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("no se econtro la categoria"));
        categoryRepository.delete(category);
    }
}
