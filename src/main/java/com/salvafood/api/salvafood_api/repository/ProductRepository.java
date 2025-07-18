package com.salvafood.api.salvafood_api.repository;

import com.salvafood.api.salvafood_api.model.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Page<ProductEntity> findByActiveTrue(Pageable pageable);

    @Query("SELECT p FROM ProductEntity p WHERE p.expiredDate <= :date AND p.active = true")
    List<ProductEntity> findProductsNearExpiration(@Param("date") LocalDate date);

    @Query("SELECT p FROM ProductEntity p WHERE p.name ILIKE %:name% AND p.active = true")
    Page<ProductEntity> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    Optional<ProductEntity> findByIdAndActiveTrue(Long id);
}