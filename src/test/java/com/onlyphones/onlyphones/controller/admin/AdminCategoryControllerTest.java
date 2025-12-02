package com.onlyphones.onlyphones.controller.admin;

import com.onlyphones.onlyphones.entity.Category;
import com.onlyphones.onlyphones.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminCategoryControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    private AdminCategoryController controller;

    @Mock
    private CategoryService serviceForSuper; // primer parámetro del constructor (usado por super)
    @Mock
    private CategoryService serviceForField; // segundo parámetro (usado en este controlador)

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new AdminCategoryController(serviceForSuper, serviceForField);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void createProduct_ReturnsOk_WhenCategoryCreated() throws Exception {
        Category returned = new Category();
        when(serviceForField.createCategory(any(Category.class))).thenReturn(returned);

        mockMvc.perform(post("/api/admin/createcategory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Category())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void createProduct_ReturnsNoContent_WhenServiceReturnsNull() throws Exception {
        when(serviceForField.createCategory(any(Category.class))).thenReturn(null);

        mockMvc.perform(post("/api/admin/createcategory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Category())))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateCategory_ReturnsOk_WhenUpdated() throws Exception {
        String id = "123";
        Category updated = new Category();
        when(serviceForField.updateCategory(eq(id), any(Category.class))).thenReturn(updated);

        mockMvc.perform(put("/api/admin/updatecategory/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Category())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateCategory_ReturnsNoContent_WhenServiceReturnsNull() throws Exception {
        String id = "123";
        when(serviceForField.updateCategory(eq(id), any(Category.class))).thenReturn(null);

        mockMvc.perform(put("/api/admin/updatecategory/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Category())))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteCategory_ReturnsOk_WhenDeleted() throws Exception {
        String id = "abc";
        doNothing().when(serviceForField).deleteCategory(id);

        mockMvc.perform(delete("/api/admin/deletecategory/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Categoria eliminada correctamente")));
    }

    @Test
    void deleteCategory_Returns500_OnException() throws Exception {
        String id = "abc";
        doThrow(new RuntimeException("boom")).when(serviceForField).deleteCategory(id);

        mockMvc.perform(delete("/api/admin/deletecategory/{id}", id))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Error al eliminar categoria")));
    }
}
