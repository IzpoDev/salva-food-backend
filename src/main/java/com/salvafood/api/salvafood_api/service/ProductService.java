package com.salvafood.api.salvafood_api.service;

import com.salvafood.api.salvafood_api.model.dto.ProductRequestDto;
import com.salvafood.api.salvafood_api.model.dto.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto productDto);
    ProductResponseDto getProductById(Long id);
    Page<ProductResponseDto> getAllProducts(Pageable pageable);
    Page<ProductResponseDto> getActiveProducts(Pageable pageable);
    Page<ProductResponseDto> searchProductsByName(String name, Pageable pageable);
    List<ProductResponseDto> getProductsNearExpiration(int days);
    ProductResponseDto updateProduct(Long id, ProductRequestDto productDto);
    void deleteProduct(Long id);
    String addImageToProduct(Long productId, String imageUrl);
    void removeImageFromProduct(Long productId, String imageUrl);
    void setMainImage(Long productId, String imageUrl);
}