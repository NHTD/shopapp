package com.example.shopapp.mapper;

import com.example.shopapp.dtos.request.ProductImageRequest;
import com.example.shopapp.dtos.response.ProductImageResponse;
import com.example.shopapp.models.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {
    @Mapping(target = "product", ignore = true)
    ProductImage productImageToProductImage(ProductImageRequest request);
    ProductImageResponse productImageToProductImageResponse(ProductImage productImage);
}
