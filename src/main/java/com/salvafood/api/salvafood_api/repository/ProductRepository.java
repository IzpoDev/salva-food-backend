package com.salvafood.api.salvafood_api.repository;

import com.salvafood.api.salvafood_api.model.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    // Métodos existentes
    Optional<ProductEntity> findByIdAndActiveTrue(Long id);

    Page<ProductEntity> findByActiveTrue(Pageable pageable);

    Page<ProductEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT p FROM ProductEntity p WHERE p.expiredDate <= :date AND p.active = true")
    List<ProductEntity> findProductsNearExpiration(@Param("date") LocalDate date);

    // Nuevos métodos simples
    List<ProductEntity> findByActiveTrue();

    List<ProductEntity> findByNameContainingIgnoreCase(String name);
}