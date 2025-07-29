package com.salvafood.api.salvafood_api.service;

import com.salvafood.api.salvafood_api.model.dto.ShoppingCartRequestDto;
import com.salvafood.api.salvafood_api.model.dto.ShoppingCartResponseDto;

public interface ShoppingCartService {

    ShoppingCartResponseDto createShoppingCart(ShoppingCartRequestDto shoppingCartRequestDto);
    ShoppingCartResponseDto getShoppingCartByIdAndUserId(Long id, Long userId);
    ShoppingCartResponseDto updateShoppingCart(Long id, ShoppingCartRequestDto shoppingCartRequestDto);
    ShoppingCartResponseDto deleteShoppingCart(Long id, Long userId);
    ShoppingCartResponseDto getShoppingCart(Long id);
}
