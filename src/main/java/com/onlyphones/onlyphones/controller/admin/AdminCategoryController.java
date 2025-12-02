package com.onlyphones.onlyphones.controller.admin;

import com.onlyphones.onlyphones.controller.CategoryAbstractController;
import com.onlyphones.onlyphones.entity.Category;
import com.onlyphones.onlyphones.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")

public class AdminCategoryController extends CategoryAbstractController {

    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService, CategoryService categoryService1) {
        super(categoryService);
        this.categoryService = categoryService1;
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
    public ResponseEntity<String> deleteCategory(@PathVariable String id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("Categoria eliminada correctamente");
        } catch(Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar categoria" + e.getMessage());
        }
    }
}
