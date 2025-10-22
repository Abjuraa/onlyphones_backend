package com.onlyphones.onlyphones.controller.admin;

import com.onlyphones.onlyphones.entity.Category;
import com.onlyphones.onlyphones.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")

public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping("/category")
    public ResponseEntity<?> getAllCategories() {
        List<Category> response = categoryService.getAllCategories();

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable String id) {
        Category response = categoryService.getCategoryById(id);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/createcategory")
    public ResponseEntity<Category> createProduct(@RequestBody Category newCategory) {
        Category response = categoryService.createCategory(newCategory);
        if (response == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping("/updatecategory/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable String id, @RequestBody Category editCategory) {
        Category response = categoryService.updateCategory(id, editCategory);

        if (response == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deletecategory/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("Categoria eliminada correctamente");
        } catch(Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar categoria" + e.getMessage());
        }
    }
}
