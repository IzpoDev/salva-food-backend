package com.salvafood.api.salvafood_api.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordDto {
    @NotBlank(message = "Token no puede estar vacío")
    private String token;
    @NotBlank(message = "La contraseña nueva no puede estar vacía")
    @Size(min = 8, message = "La contraseña nueva debe tener al menos 8 caracteres")
    @Size(max = 64, message = "La contraseña nueva no puede exceder los 64 caracteres")
    private String newPassword;
}
