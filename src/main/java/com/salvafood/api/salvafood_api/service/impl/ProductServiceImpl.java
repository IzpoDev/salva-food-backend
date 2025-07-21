package com.salvafood.api.salvafood_api.service.impl;

import com.salvafood.api.salvafood_api.mapper.ProductMapper;
import com.salvafood.api.salvafood_api.model.dto.ProductRequestDto;
import com.salvafood.api.salvafood_api.model.dto.ProductResponseDto;
import com.salvafood.api.salvafood_api.model.entity.ProductEntity;
import com.salvafood.api.salvafood_api.repository.ProductRepository;
import com.salvafood.api.salvafood_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productDto) {
        ProductEntity product = ProductMapper.toEntity(productDto);
        ProductEntity savedProduct = productRepository.save(product);
        return ProductMapper.toResponseDto(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long id) {
        ProductEntity product = productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        return ProductMapper.toResponseDto(product);
    }

    // Métodos paginados existentes
    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getActiveProducts(Pageable pageable) {
        return productRepository.findByActiveTrue(pageable)
                .map(ProductMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> searchProductsByName(String name, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(ProductMapper::toResponseDto);
    }

    // Nuevos métodos simples
    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProductsSimple() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllActiveProductsSimple() {
        return productRepository.findByActiveTrue()
                .stream()
                .map(ProductMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> searchProductsByNameSimple(String name) {
        return productRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(ProductMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getProductsNearExpiration(int days) {
        LocalDate targetDate = LocalDate.now().plusDays(days);
        return productRepository.findProductsNearExpiration(targetDate)
                .stream()
                .map(ProductMapper::toResponseDto)
                .toList();
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productDto) {
        ProductEntity product = productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        ProductMapper.updateEntityFromDto(productDto, product);
        ProductEntity updatedProduct = productRepository.save(product);
        return ProductMapper.toResponseDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        product.setActive(false);
        productRepository.save(product);
    }

    @Override
    public String addImageToProduct(Long productId, String imageUrl) {
        ProductEntity product = productRepository.findByIdAndActiveTrue(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productId));

        product.getImageUrls().add(imageUrl);

        if (product.getMainImageUrl() == null) {
            product.setMainImageUrl(imageUrl);
        }

        productRepository.save(product);
        return imageUrl;
    }

    @Override
    public void removeImageFromProduct(Long productId, String imageUrl) {
        ProductEntity product = productRepository.findByIdAndActiveTrue(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productId));

        product.getImageUrls().remove(imageUrl);

        if (imageUrl.equals(product.getMainImageUrl())) {
            product.setMainImageUrl(product.getImageUrls().isEmpty() ? null : product.getImageUrls().get(0));
        }

        productRepository.save(product);
    }

    @Override
    public void setMainImage(Long productId, String imageUrl) {
        ProductEntity product = productRepository.findByIdAndActiveTrue(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productId));

        if (!product.getImageUrls().contains(imageUrl)) {
            throw new RuntimeException("La imagen no pertenece a este producto");
        }

        product.setMainImageUrl(imageUrl);
        productRepository.save(product);
    }
}