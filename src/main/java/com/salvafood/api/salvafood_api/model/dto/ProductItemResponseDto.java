package com.salvafood.api.salvafood_api.model.dto;

import com.salvafood.api.salvafood_api.model.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductItemResponseDto {
    private String name;
    private Double price;
    private Double originalPrice;
    private Double discountPercentage;
    private LocalDate expiredDate;
    private List<String> imageUrls;
    private String mainImageUrl;
    private Integer quantity;
    private Double subTotalPrice;

    //Constructor personalizado
    public ProductItemResponseDto(ProductEntity productEntity, Integer quantity, Double subTotalPrice) {
        this.name = productEntity.getName();
        this.price = productEntity.getPrice();
        this.originalPrice = productEntity.getOriginalPrice();
        this.discountPercentage = productEntity.getDiscountPercentage();
        this.expiredDate = productEntity.getExpiredDate();
        this.imageUrls = productEntity.getImageUrls();
        this.mainImageUrl = productEntity.getMainImageUrl();
        this.quantity = quantity;
        this.subTotalPrice = subTotalPrice;
    }
}
