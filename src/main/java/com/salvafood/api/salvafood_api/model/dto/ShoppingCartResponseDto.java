package com.salvafood.api.salvafood_api.model.dto;

import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartResponseDto {
    private Long id;
    private List<ProductItemResponseDto> productItemList;
    private Double totalPrice;
    private Long userId;
}
