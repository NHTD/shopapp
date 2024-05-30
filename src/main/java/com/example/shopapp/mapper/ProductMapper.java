package com.example.shopapp.mapper;

import com.example.shopapp.dtos.request.ProductCreationRequest;
import com.example.shopapp.dtos.request.ProductUpdateRequest;
import com.example.shopapp.dtos.response.ProductResponse;
import com.example.shopapp.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "category", ignore = true)
    Product productToProduct(ProductCreationRequest request);
    ProductResponse productToProductResponse(Product product);

    void productToUpdateProduct(@MappingTarget Product product, ProductUpdateRequest request);
}
