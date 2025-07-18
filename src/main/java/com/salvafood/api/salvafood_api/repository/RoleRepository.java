package com.salvafood.api.salvafood_api.repository;

import com.salvafood.api.salvafood_api.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    @Query("SELECT r FROM RoleEntity r WHERE r.name =:name")
    RoleEntity findByName(@Param("name") String name);

    @Query("SELECT r FROM RoleEntity r WHERE r.id =:id")
    Optional<RoleEntity> findById(@Param("id") Long id);

    @Query("SELECT r FROM RoleEntity r WHERE r.active=TRUE")
    Optional<List<RoleEntity>> findAllroles();
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM RoleEntity r WHERE r.name =:name")
    Boolean existsByName(@Param("name") String name);
}
