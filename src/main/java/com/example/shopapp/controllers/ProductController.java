package com.example.shopapp.controllers;

import com.example.shopapp.dtos.request.ProductCreationRequest;
import com.example.shopapp.dtos.request.ProductImageRequest;
import com.example.shopapp.dtos.request.ProductUpdateRequest;
import com.example.shopapp.dtos.response.EntityResponse;
import com.example.shopapp.dtos.response.ProductImageResponse;
import com.example.shopapp.dtos.response.ProductListResponse;
import com.example.shopapp.dtos.response.ProductResponse;
import com.example.shopapp.exception.ShopAppFileTooLargeException;
import com.example.shopapp.exception.ShopAppFileUnSupportedMediaTypeException;
import com.example.shopapp.models.Product;
import com.example.shopapp.services.ProductService;
import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    ProductService productService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('UPDATE_DATA')")
    EntityResponse<ProductResponse> create(
            @RequestBody @Valid ProductCreationRequest request
    ){
//        ProductResponse product = productService.createProduct(request);

        return EntityResponse.<ProductResponse>builder()
                .status(true)
                .body(productService.createProduct(request))
                .build();
    }

    @PostMapping(value = "/uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('READ_DATA')")
    public EntityResponse<?> uploadImages(
            @PathVariable("id") Long productId,
            @ModelAttribute("files") List<MultipartFile> files
    ) throws IOException {
//        List<MultipartFile> files = request.getFiles();
        ProductResponse product = productService.getProductById(productId);
        files = files==null ? new ArrayList<MultipartFile>() :files;

        if(files.size()>5){
            throw new ShopAppFileTooLargeException("You can only upload maximum 5 images");
        }

        List<ProductImageResponse> productImages = new ArrayList<>();

        for(MultipartFile file : files){
            if(file.getSize() == 0){
                continue;
            }

            if(file.getSize() > 10 * 1024 * 1024){
                throw new ShopAppFileTooLargeException("File is too large! Maximum size is 10MB");
            }

            String contentType = file.getContentType();

            if(contentType==null || !contentType.startsWith("image/")){
                throw new ShopAppFileUnSupportedMediaTypeException("File must be an image");
            }

            String fileName = storeFile(file);
//            request.setThumbnail(fileName);
            ProductImageResponse productImage = productService.createProductImage(product.getId(), ProductImageRequest.builder().imageUrl(fileName).build());
            productImages.add(productImage);
        }
        return EntityResponse.builder()
                .status(true)
                .body(productImages)
                .build();
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable("imageName") String imageName) throws MalformedURLException {
        Path imagePath = Paths.get("uploads/" + imageName);
        UrlResource resource = new UrlResource(imagePath.toUri());

        if(resource.exists()){
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        if(!isImageFile(file) || file.getOriginalFilename()==null){
            throw new ShopAppFileUnSupportedMediaTypeException("Invalid image format");
        }

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        String uniqueFilename = UUID.randomUUID().toString() + "_" + fileName;

        Path uploadDir = Paths.get("uploads");
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }

        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);

        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFilename;
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType!=null && contentType.startsWith("image/");
    }

    @GetMapping
    EntityResponse<ProductListResponse> getProducts(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name = "category_id") Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ){
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                Sort.by("id").ascending()
        );
        Page<ProductResponse> productPage = productService.getProducts(pageRequest, categoryId, keyword);
        int totalPages = productPage.getTotalPages();
        List<ProductResponse> products = productPage.getContent();

        return EntityResponse.<ProductListResponse>builder()
                .status(true)
                .body(ProductListResponse.builder().products(products).totalPages(totalPages).build())
                .build();
    }

    @GetMapping("/{id}")
    EntityResponse<ProductResponse> getProduct(@PathVariable("id") Long id){
        return EntityResponse.<ProductResponse>builder()
                .status(true)
                .body(productService.getProductById(id))
                .build();
    }

    @PutMapping("/{id}")
    EntityResponse<ProductResponse> updateProduct(@PathVariable("id") Long id, @RequestBody ProductUpdateRequest request){
        return EntityResponse.<ProductResponse>builder()
                .status(true)
                .body(productService.updateProduct(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    EntityResponse<String> deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return EntityResponse.<String>builder()
                .status(true)
                .body("Delete is successful")
                .build();
    }

    @PostMapping("/generateFakerProducts")
    public EntityResponse.EntityResponseBuilder<String> generateFaker() {
        Faker faker = new Faker();
        for(int i=0; i<1000000; i++){
            String productName = faker.commerce().productName();
            if(productService.existsByName(productName)){
                continue;
            }
            ProductCreationRequest request = ProductCreationRequest.builder()
                    .name(productName)
                    .price((float)faker.number().numberBetween(10, 90000000))
                    .description(faker.lorem().sentence())
                    .categoryId((long) faker.number().numberBetween(12, 15))
                    .build();
            productService.createProduct(request);
        }
        return EntityResponse.<String>builder().status(true).body("Create successfully");
    }

    @GetMapping("/by-ids")
    public EntityResponse<List<Product>> getProductByIds(@RequestParam("ids") String ids){
        List<Long> productIds = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        List<Product> products = productService.findProductByIds(productIds);
        return EntityResponse.<List<Product>>builder()
                .status(true)
                .body(products)
                .build();

    }

}
