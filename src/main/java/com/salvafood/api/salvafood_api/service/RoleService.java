package com.salvafood.api.salvafood_api.service;

import com.salvafood.api.salvafood_api.model.dto.RoleRequestDto;
import com.salvafood.api.salvafood_api.model.dto.RoleResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    RoleResponseDto createRole(RoleRequestDto roleRequestDto);
    RoleResponseDto updateRole(RoleRequestDto roleRequestDto, Long id);
    List<RoleResponseDto> getAllRoles();
    RoleResponseDto getRoleById(Long id);
    void deleteRole(Long id);
}
