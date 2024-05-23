package com.example.shopapp.services.imp;

import com.example.shopapp.dtos.request.CategoryCreateRequest;
import com.example.shopapp.dtos.response.CategoryResponse;
import com.example.shopapp.mapper.CategoryMapper;
import com.example.shopapp.models.Category;
import com.example.shopapp.repositories.CategoryRepository;
import com.example.shopapp.services.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImp implements CategoryService {

    CategoryMapper categoryMapper;
    CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CategoryCreateRequest request) {
        Category category = categoryMapper.categoryToCategory(request);

        return categoryMapper.categoryToCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse getCategoryById(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));

        return categoryMapper.categoryToCategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(categoryMapper::categoryToCategoryResponse).collect(Collectors.toList());
    }

    @Override
    public CategoryResponse updateCategory(long id, CategoryCreateRequest request) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));

        categoryMapper.categoryToUpdateCategory(category, request);

        return categoryMapper.categoryToCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }
}
