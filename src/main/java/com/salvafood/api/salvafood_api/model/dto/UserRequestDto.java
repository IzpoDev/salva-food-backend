package com.salvafood.api.salvafood_api.model.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@NoArgsConstructor
@Getter
@Setter
public class UserRequestDto {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;
    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "Este campo debe tener el formato de email **@Sudominio.com")
    private String email;
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;
    @NotNull(message = "El rol no puede estar vacío")
    @Min(value = 1, message = "El rol debe ser mayor a 0")
    @Max(value = 3, message = "El rol debe ser menor o igual a 3")
    private Long roleId;
    @NotBlank(message = "El número de teléfono no puede estar vacío")
    private String phoneNumber;
}
