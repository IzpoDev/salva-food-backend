package com.salvafood.api.salvafood_api.service;

import com.salvafood.api.salvafood_api.model.dto.ProductRequestDto;
import com.salvafood.api.salvafood_api.model.dto.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto productDto);
    ProductResponseDto getProductById(Long id);

    // Métodos paginados
    Page<ProductResponseDto> getAllProducts(Pageable pageable);
    Page<ProductResponseDto> getActiveProducts(Pageable pageable);
    Page<ProductResponseDto> searchProductsByName(String name, Pageable pageable);

    // Métodos simples para el frontend
    List<ProductResponseDto> getAllProductsSimple();
    List<ProductResponseDto> getAllActiveProductsSimple();
    List<ProductResponseDto> searchProductsByNameSimple(String name);

    List<ProductResponseDto> getProductsNearExpiration(int days);
    ProductResponseDto updateProduct(Long id, ProductRequestDto productDto);
    void deleteProduct(Long id);
    String addImageToProduct(Long productId, MultipartFile image) throws IOException;
    void removeImageFromProduct(Long productId, String imageUrl);
    void setMainImage(Long productId, String imageUrl);
}