package com.salvafood.api.salvafood_api.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {
    private Long id;
    private Long shoppingCartId;
    private Double amount;
    private String type;
    private String codigo;
}
