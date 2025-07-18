package com.salvafood.api.salvafood_api.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoginRequestDto {
    @NotBlank(message = "El email no puede ser nulo o estar en blanco")
    @Email(message = "Este campo debe tener formato de email **@sudominio.com")
    private String email;
    @NotBlank(message = "La contraseña no puede ser nula o estar en blanco")
    private String password;
}
