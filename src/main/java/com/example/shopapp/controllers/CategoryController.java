package com.example.shopapp.controllers;

import com.example.shopapp.dtos.request.CategoryCreateRequest;
import com.example.shopapp.dtos.response.CategoryResponse;
import com.example.shopapp.dtos.response.EntityResponse;
import com.example.shopapp.services.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {

    CategoryService categoryService;

    @PostMapping
    EntityResponse<CategoryResponse> create(@RequestBody CategoryCreateRequest request){
        return EntityResponse.<CategoryResponse>builder()
                .status(true)
                .body(categoryService.createCategory(request))
                .build();
    }

    @GetMapping
    EntityResponse<List<CategoryResponse>> getAllCategories(){
        return EntityResponse.<List<CategoryResponse>>builder()
                .status(true)
                .body(categoryService.getAllCategories())
                .build();
    }

    @GetMapping("/{id}")
    EntityResponse<CategoryResponse> getAllCategories(@PathVariable("id") Long id){
        return EntityResponse.<CategoryResponse>builder()
                .status(true)
                .body(categoryService.getCategoryById(id))
                .build();
    }

    @PutMapping("/{id}")
    EntityResponse<CategoryResponse> updateCategory(
            @PathVariable("id") Long id,
            @RequestBody CategoryCreateRequest request
    ){
        return EntityResponse.<CategoryResponse>builder()
                .status(true)
                .body(categoryService.updateCategory(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    EntityResponse<String> updateCategory(
            @PathVariable("id") Long id
    ){
        categoryService.deleteCategory(id);
        return EntityResponse.<String>builder()
                .status(true)
                .body("Delete successfully")
                .build();
    }
}
