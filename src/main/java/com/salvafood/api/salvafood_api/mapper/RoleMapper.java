package com.salvafood.api.salvafood_api.mapper;

import com.salvafood.api.salvafood_api.model.dto.RoleRequestDto;
import com.salvafood.api.salvafood_api.model.dto.RoleResponseDto;
import com.salvafood.api.salvafood_api.model.entity.RoleEntity;

import java.util.List;
import java.util.stream.Collectors;


public class RoleMapper {

    public static RoleEntity toEntity(RoleRequestDto roleRequestDto){
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(roleRequestDto.getName());
        roleEntity.setDescription(roleRequestDto.getDescription());
        return roleEntity;
    }
    public static RoleResponseDto toDto(RoleEntity roleEntity){
        RoleResponseDto roleResponseDto = new RoleResponseDto();
        roleResponseDto.setId(roleEntity.getId());
        roleResponseDto.setName(roleEntity.getName());
        roleResponseDto.setDescription(roleEntity.getDescription());
        roleResponseDto.setCreatedAt(roleEntity.getCreatedAt());
        roleResponseDto.setUpdatedAt(roleEntity.getUpdatedAt());
        roleResponseDto.setCreatedBy(roleEntity.getCreatedBy());
        roleResponseDto.setUpdatedBy(roleEntity.getUpdatedBy());
        return roleResponseDto;
    }
    public static List<RoleResponseDto> toListDto(List<RoleEntity> roleEntities){
        return roleEntities.stream()
                .map(RoleMapper::toDto)
                .collect(Collectors.toList());
    }
}
