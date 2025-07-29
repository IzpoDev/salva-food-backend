package com.salvafood.api.salvafood_api.repository;

import com.salvafood.api.salvafood_api.model.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {

    @Query("SELECT sc FROM ShoppingCartEntity sc WHERE sc.id=:id AND sc.user.id=:userId")
    ShoppingCartEntity findByIdAndUserId(@Param("id") Long id,@Param("userId") Long userId);
}
