package com.salvafood.api.salvafood_api.repository;

import com.salvafood.api.salvafood_api.model.entity.TokenResetPasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenResetPasswordRepository extends JpaRepository<TokenResetPasswordEntity, Long> {

    // Method to find a token by its value
    Optional<TokenResetPasswordEntity> findByToken(String token);

    // Method to delete a token by its value
    void deleteByToken(String token);

    // Method to check if a token exists
    boolean existsByToken(String token);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM TokenResetPasswordEntity t WHERE t.token = :token AND t.active = :active")
    boolean existsByTokenAndActive(String token, Boolean active);
}
