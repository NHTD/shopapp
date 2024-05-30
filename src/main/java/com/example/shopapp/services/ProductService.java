package com.example.shopapp.services;

import com.example.shopapp.dtos.request.ProductCreationRequest;
import com.example.shopapp.dtos.request.ProductImageRequest;
import com.example.shopapp.dtos.request.ProductUpdateRequest;
import com.example.shopapp.dtos.response.ProductImageResponse;
import com.example.shopapp.dtos.response.ProductResponse;
import com.example.shopapp.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    ProductResponse createProduct(ProductCreationRequest request);
    ProductResponse getProductById(long id);
    Page<ProductResponse> getProducts(PageRequest pageRequest);
    ProductResponse updateProduct(long id, ProductUpdateRequest request);
    void deleteProduct(long id);
    boolean existsByName(String name);
    ProductImageResponse createProductImage(Long productId, ProductImageRequest request);
}
