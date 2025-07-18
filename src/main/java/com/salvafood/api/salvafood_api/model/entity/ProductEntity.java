package com.salvafood.api.salvafood_api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_product", nullable = false)
    private String name;

    @Column(name = "description_product", nullable = false)
    private String description;

    @Column(name = "price_product", nullable = false)
    private Double price;

    @Column(name = "original_price", nullable = false)
    private Double originalPrice;

    @Column(name = "stock_product", nullable = false)
    private Integer stock;

    @Column(name = "discount_percentage", nullable = false)
    private Double discountPercentage;

    @Column(name = "expired_date", nullable = false)
    private LocalDate expiredDate;

    // URLs de las imágenes
    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;

    @Column(name = "main_image_url")
    private String mainImageUrl;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    // Método para calcular días hasta vencimiento
    public long getDaysUntilExpiration() {
        return ChronoUnit.DAYS.between(LocalDate.now(), this.expiredDate);
    }

    // Método para verificar si está próximo a vencer
    public boolean isNearExpiration(int days) {
        return getDaysUntilExpiration() <= days;
    }
}
