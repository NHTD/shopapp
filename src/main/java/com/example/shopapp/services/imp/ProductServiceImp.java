package com.example.shopapp.services.imp;

import com.example.shopapp.dtos.request.ProductCreationRequest;
import com.example.shopapp.dtos.request.ProductImageRequest;
import com.example.shopapp.dtos.request.ProductUpdateRequest;
import com.example.shopapp.dtos.response.ProductImageResponse;
import com.example.shopapp.dtos.response.ProductResponse;
import com.example.shopapp.exception.ShopAppFileTooLargeException;
import com.example.shopapp.exception.ShopAppModelsNotFoundException;
import com.example.shopapp.mapper.ProductImageMapper;
import com.example.shopapp.mapper.ProductMapper;
import com.example.shopapp.models.Category;
import com.example.shopapp.models.Product;
import com.example.shopapp.models.ProductImage;
import com.example.shopapp.repositories.CategoryRepository;
import com.example.shopapp.repositories.ProductImageRepository;
import com.example.shopapp.repositories.ProductRepository;
import com.example.shopapp.services.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImp implements ProductService {

    CategoryRepository categoryRepository;
    ProductRepository productRepository;
    ProductImageRepository productImageRepository;

    ProductMapper productMapper;
    ProductImageMapper productImageMapper;

    @Override
    public ProductResponse createProduct(ProductCreationRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ShopAppModelsNotFoundException("Cannot find category with id {} ", request.getCategoryId()));
        Product product = productMapper.productToProduct(request);

        product.setCategory(category);

        return productMapper.productToProductResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse getProductById(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ShopAppModelsNotFoundException("Product with id {} is not existed", id));

        List<ProductImage> productImage = productImageRepository.findByProductId(id);
        product.setProductImages(productImage);

        return productMapper.productToProductResponse(product);
    }

    @Override
    public Page<ProductResponse> getProducts(PageRequest pageRequest, Long categoryId, String keyword) {
        Page<Product> productPage = productRepository.searchProducts(categoryId, keyword, pageRequest);
        return productPage.map(productMapper::productToProductResponse);
    }

    @Override
    public ProductResponse updateProduct(long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ShopAppModelsNotFoundException("Product with id {} is not existed", id));

        categoryRepository.findById(request.getCategoryId())
                        .orElseThrow(() -> new ShopAppModelsNotFoundException("Cannot find category with id ", request.getCategoryId()));

        productMapper.productToUpdateProduct(product, request);

        return productMapper.productToProductResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()){
            productRepository.deleteById(id);
        }
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImageResponse createProductImage(Long productId, ProductImageRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ShopAppModelsNotFoundException("Product is not existed with id {}", request.getProductId()));

        ProductImage productImage = productImageMapper.productImageToProductImage(request);

        productImage.setProduct(product);

        int size = productImageRepository.findByProductId(productId).size();
        if(size>=5){
            throw new ShopAppFileTooLargeException("Number of images must be <= 5");
        }

        return productImageMapper.productImageToProductImageResponse(productImageRepository.save(productImage));
    }

    @Override
    public List<Product> findProductByIds(List<Long> productIds) {
        return productRepository.findProductByIds(productIds);
    }
}
