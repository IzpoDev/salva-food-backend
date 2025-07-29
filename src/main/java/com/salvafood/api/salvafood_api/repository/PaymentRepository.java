package com.salvafood.api.salvafood_api.repository;

import com.salvafood.api.salvafood_api.model.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    @Query("SELECT p FROM PaymentEntity p WHERE p.shoppingCart.id=:scId")
    PaymentEntity findByShoppingCartId(@Param("scId") Long shoppingCartId);
}
