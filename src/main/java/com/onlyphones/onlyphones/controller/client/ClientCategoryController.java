package com.onlyphones.onlyphones.controller.client;

import com.onlyphones.onlyphones.controller.CategoryAbstractController;
import com.onlyphones.onlyphones.service.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/client")

public class ClientCategoryController extends CategoryAbstractController {

    public ClientCategoryController(CategoryService categoryService) {
        super(categoryService);
    }
}
