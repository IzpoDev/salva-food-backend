package com.salvafood.api.salvafood_api.repository;

import com.salvafood.api.salvafood_api.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.email =:email")
    Optional<UserEntity> findByEmail(@Param("email") String email);
    @Query("SELECT u FROM UserEntity u WHERE u.phoneNumber =:phone")
    UserEntity findByPhoneNumber(@Param("phone") String phoneNumber);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserEntity u WHERE u.email =:email")
    boolean existsByEmail(@Param("email") String email);
    boolean existsByPhoneNumber(String phoneNumber);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserEntity u WHERE u.id =:id AND u.active =:active")
    Boolean existsByIdAndActive(@Param("id") Long id,@Param("active") Boolean active);
    @Query("SELECT u FROM UserEntity u WHERE u.email=:username and u.active=:aTrue")
    Optional<UserEntity> findByUsernameAndActive(@Param("username") String username,@Param("aTrue") Boolean aTrue);
    @Query("SELECT u FROM UserEntity u WHERE u.active = true")
    Optional<List<UserEntity>> findAllUsers();
    @Query("SELECT u FROM UserEntity u WHERE u.id =:id")
    Optional<UserEntity> findById(@Param("id") Long id);
}
