package com.salvafood.api.salvafood_api.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequestDto {
    @NotBlank(message = "El nombre del rol no debe estar en blanco")
    private String name;
    @NotBlank(message = "La descripcion no debe ser nula o estar en blanco")
    private String description;
}
