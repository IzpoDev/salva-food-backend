package com.salvafood.api.salvafood_api.mapper;

import com.salvafood.api.salvafood_api.model.dto.ProductRequestDto;
import com.salvafood.api.salvafood_api.model.dto.ProductResponseDto;
import com.salvafood.api.salvafood_api.model.entity.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public static ProductEntity toEntity(ProductRequestDto dto) {
        ProductEntity entity = new ProductEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setOriginalPrice(dto.getOriginalPrice());
        entity.setStock(dto.getStock());
        entity.setExpiredDate(dto.getExpiredDate());
        entity.setActive(dto.getActive());
        entity.setImageUrls(new ArrayList<>());

        // Calcular porcentaje de descuento
        double discount = ((dto.getOriginalPrice() - dto.getPrice()) / dto.getOriginalPrice()) * 100;
        entity.setDiscountPercentage(Math.round(discount * 100.0) / 100.0);

        return entity;
    }

    public static ProductResponseDto toResponseDto(ProductEntity entity) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setOriginalPrice(entity.getOriginalPrice());
        dto.setStock(entity.getStock());
        dto.setDiscountPercentage(entity.getDiscountPercentage());
        dto.setExpiredDate(entity.getExpiredDate());
        dto.setImageUrls(entity.getImageUrls());
        dto.setMainImageUrl(entity.getMainImageUrl());
        dto.setActive(entity.getActive());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setDaysUntilExpiration(entity.getDaysUntilExpiration());
        dto.setNearExpiration(entity.isNearExpiration(7));

        return dto;
    }

    public static void updateEntityFromDto(ProductRequestDto dto, ProductEntity entity) {
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
        if (dto.getPrice() != null) {
            entity.setPrice(dto.getPrice());
            // Recalcular descuento
            double discount = ((entity.getOriginalPrice() - dto.getPrice()) / entity.getOriginalPrice()) * 100;
            entity.setDiscountPercentage(Math.round(discount * 100.0) / 100.0);
        }
        if (dto.getOriginalPrice() != null) {
            entity.setOriginalPrice(dto.getOriginalPrice());
            // Recalcular descuento
            double discount = ((dto.getOriginalPrice() - entity.getPrice()) / dto.getOriginalPrice()) * 100;
            entity.setDiscountPercentage(Math.round(discount * 100.0) / 100.0);
        }
        if (dto.getStock() != null) entity.setStock(dto.getStock());
        if (dto.getExpiredDate() != null) entity.setExpiredDate(dto.getExpiredDate());
        if (dto.getActive() != null) entity.setActive(dto.getActive());
    }

    public static List<ProductResponseDto> toListDto(List<ProductEntity> productEntities){
        return productEntities.stream()
                .map(ProductMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}