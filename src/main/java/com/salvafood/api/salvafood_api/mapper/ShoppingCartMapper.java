package com.salvafood.api.salvafood_api.mapper;

import com.salvafood.api.salvafood_api.model.dto.ProductItemResponseDto;
import com.salvafood.api.salvafood_api.model.dto.ShoppingCartResponseDto;
import com.salvafood.api.salvafood_api.model.entity.ShoppingCartEntity;

import java.util.List;

public class ShoppingCartMapper {


    public static ShoppingCartResponseDto toDto(ShoppingCartEntity shoppingCartEntity,
                                                List<ProductItemResponseDto> productItemResponseDtoList) {
        ShoppingCartResponseDto shoppingCartResponseDto = new ShoppingCartResponseDto();
        shoppingCartResponseDto.setId(shoppingCartEntity.getId());
        shoppingCartResponseDto.setUserId(shoppingCartEntity.getUser().getId());
        shoppingCartResponseDto.setProductItemList(productItemResponseDtoList);
        shoppingCartResponseDto.setTotalPrice(shoppingCartEntity.getTotal());
        return shoppingCartResponseDto;
    }
}
