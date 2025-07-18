package com.salvafood.api.salvafood_api.service.impl;

import com.salvafood.api.salvafood_api.exception.EntityNotFoundException;
import com.salvafood.api.salvafood_api.mapper.RoleMapper;
import com.salvafood.api.salvafood_api.model.dto.RoleRequestDto;
import com.salvafood.api.salvafood_api.model.dto.RoleResponseDto;
import com.salvafood.api.salvafood_api.model.entity.RoleEntity;
import com.salvafood.api.salvafood_api.repository.RoleRepository;
import com.salvafood.api.salvafood_api.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleResponseDto createRole(RoleRequestDto roleRequestDto) {
        RoleEntity roleEntity = RoleMapper.toEntity(roleRequestDto);
        if(roleRepository.findByName(roleEntity.getName()) != null){
            throw new RuntimeException("Ya existe un rol con el nombre: " + roleEntity.getName());
        }
        roleEntity.setActive(Boolean.TRUE);
        return RoleMapper.toDto(roleRepository.save(roleEntity));
    }

    @Override
    public RoleResponseDto updateRole(RoleRequestDto roleRequestDto, Long id) {
        RoleEntity roleEntity = RoleMapper.toEntity(roleRequestDto);
        if(roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El role no existe")) != null){
            roleEntity.setId(id);
            return RoleMapper.toDto(roleRepository.save(roleEntity));
        }
        throw new EntityNotFoundException("El role no existe");
    }

    @Override
    public List<RoleResponseDto> getAllRoles() {
        return RoleMapper.toListDto(roleRepository.findAllroles()
                .orElseThrow(()-> new EntityNotFoundException("No se encontro los roles solicitados")));
    }

    @Override
    public RoleResponseDto getRoleById(Long id) {
        return RoleMapper.toDto(roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El role no existe con el id: "+id)));
    }

    @Override
    public void deleteRole(Long id) {
        RoleEntity roleEntity = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El role no existe con el id: " + id));
        roleEntity.setActive(Boolean.FALSE);
        roleRepository.save(roleEntity);
    }
}
