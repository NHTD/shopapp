package com.example.shopapp.services;

import com.example.shopapp.dtos.request.CategoryCreateRequest;
import com.example.shopapp.dtos.response.CategoryResponse;
import com.example.shopapp.models.Category;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryCreateRequest request);
    CategoryResponse getCategoryById(long id);
    List<CategoryResponse> getAllCategories();
    CategoryResponse updateCategory(long id, CategoryCreateRequest request);
    void deleteCategory(long id);
}
