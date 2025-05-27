package com.example.ecommerce.service;

import com.example.ecommerce.dto.CategoryDto;
import com.example.ecommerce.dto.Response;

public interface CategoryService {
    Response createCategory(CategoryDto categoryRequest);

    Response updateCategory(Long categoryId, CategoryDto categoryRequest);

    Response getAllCategory();

    Response getCategoryById(Long categoryId);

    Response deleteCategory(Long categoryId);

}
