package com.salvafood.api.salvafood_api.repository;

import com.salvafood.api.salvafood_api.model.entity.PurchaseDetailEntity;
import com.salvafood.api.salvafood_api.model.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseDetailRepository extends JpaRepository<PurchaseDetailEntity, Long> {

    @Query("SELECT pd FROM PurchaseDetailEntity pd WHERE pd.shoppingCartEntity.id=:scId")
    Optional<List<PurchaseDetailEntity>> findByShoppingCart(@Param("scId") Long shoppingCartId);
}
