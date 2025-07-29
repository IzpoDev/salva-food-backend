package com.salvafood.api.salvafood_api.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {
    @NotNull(message = "El id del carrito de compras no debe ser nulo")
    private Long shoppingCartId;
    @NotBlank(message = "El tipo de pago no puede ser nulo o en blanco")
    private String type;
}
