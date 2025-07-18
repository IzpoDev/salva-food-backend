package com.salvafood.api.salvafood_api.controller;

import com.salvafood.api.salvafood_api.model.dto.RoleRequestDto;
import com.salvafood.api.salvafood_api.model.dto.RoleResponseDto;
import com.salvafood.api.salvafood_api.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<RoleResponseDto> createRole(@RequestBody @Valid RoleRequestDto roleRequestDto){
        RoleResponseDto roleResponseDto = roleService.createRole(roleRequestDto);
        return new ResponseEntity<>(roleResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable("id") Long id){
        RoleResponseDto roleResponseDto = roleService.getRoleById(id);
        return new ResponseEntity<>(roleResponseDto, HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<RoleResponseDto>> getAllRoles(){
        List<RoleResponseDto> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RoleResponseDto> updateRole(@RequestBody @Valid RoleRequestDto roleRequestDto,
                                                      @PathVariable("id") Long id) {
        RoleResponseDto roleResponseDto = roleService.updateRole(roleRequestDto, id);
        return new ResponseEntity<>(roleResponseDto, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable("id") Long id){
        roleService.deleteRole(id);
        return new ResponseEntity<>("Role eliminado con el id: " + id , HttpStatus.NO_CONTENT);
    }
}
