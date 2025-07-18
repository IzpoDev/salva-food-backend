package com.salvafood.api.salvafood_api.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Double originalPrice;
    private Integer stock;
    private Double discountPercentage;
    private LocalDate expiredDate;
    private List<String> imageUrls;
    private String mainImageUrl;
    private Boolean active;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private Long daysUntilExpiration;
    private Boolean nearExpiration;
}