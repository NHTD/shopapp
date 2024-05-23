package com.example.shopapp.mapper;

import com.example.shopapp.dtos.request.CategoryCreateRequest;
import com.example.shopapp.dtos.response.CategoryResponse;
import com.example.shopapp.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category categoryToCategory(CategoryCreateRequest request);
    CategoryResponse categoryToCategoryResponse(Category category);
    void categoryToUpdateCategory(@MappingTarget Category category, CategoryCreateRequest request);
}
