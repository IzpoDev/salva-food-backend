package com.salvafood.api.salvafood_api.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartRequestDto {
    @NotBlank(message = "La lista de productos no puede ser nula")
    private List<Long> productosIds;
    @NotBlank(message = "La lista de cantidades no puede ser nula")
    private List<Integer> quantities;
    @NotNull(message = "El id no puede ser nulo")
    private Long userId;
}
