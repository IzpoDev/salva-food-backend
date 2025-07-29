package com.salvafood.api.salvafood_api.service.impl;

import com.salvafood.api.salvafood_api.mapper.ShoppingCartMapper;
import com.salvafood.api.salvafood_api.model.dto.ProductItemResponseDto;
import com.salvafood.api.salvafood_api.model.dto.ShoppingCartRequestDto;
import com.salvafood.api.salvafood_api.model.dto.ShoppingCartResponseDto;
import com.salvafood.api.salvafood_api.model.entity.ProductEntity;
import com.salvafood.api.salvafood_api.model.entity.PurchaseDetailEntity;
import com.salvafood.api.salvafood_api.model.entity.ShoppingCartEntity;
import com.salvafood.api.salvafood_api.repository.ProductRepository;
import com.salvafood.api.salvafood_api.repository.PurchaseDetailRepository;
import com.salvafood.api.salvafood_api.repository.ShoppingCartRepository;
import com.salvafood.api.salvafood_api.repository.UserRepository;
import com.salvafood.api.salvafood_api.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ProductRepository productRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final PurchaseDetailRepository purchaseDetailRepository;

    @Override
    public ShoppingCartResponseDto createShoppingCart(ShoppingCartRequestDto shoppingCartRequestDto) {
        //Variables iniciales
        ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();
        shoppingCartEntity.setUser(userRepository.getReferenceById(shoppingCartRequestDto.getUserId()));
        shoppingCartEntity.setActive(Boolean.TRUE);
        //Se guarda para tener el borrador del carrito guardado
        shoppingCartEntity = shoppingCartRepository.save(shoppingCartEntity);
        Double total = 0.0;
        int indexCantidad = 0;
        //Hacemos el calculo del total y la elaboracion del detalle
        List<ProductItemResponseDto> products = new ArrayList<>();
        for(Long id:shoppingCartRequestDto.getProductosIds()){
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            ProductEntity productTemp = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            Double subTotal = productTemp.getPrice() * shoppingCartRequestDto.getQuantities().get(indexCantidad);
            total += subTotal;

            purchaseDetailEntity.setShoppingCartEntity(shoppingCartEntity);
            purchaseDetailEntity.setProductEntity(productTemp);
            purchaseDetailEntity.setQuantity(shoppingCartRequestDto.getQuantities().get(indexCantidad));
            purchaseDetailEntity.setSubTotal(subTotal);
            products.add(new ProductItemResponseDto(productTemp,
                    shoppingCartRequestDto.getQuantities().get(indexCantidad),
                    subTotal));
            purchaseDetailRepository.save(purchaseDetailEntity);
            indexCantidad++;
        }
        shoppingCartEntity.setTotal(total);
        shoppingCartEntity = shoppingCartRepository.save(shoppingCartEntity);
       return ShoppingCartMapper.toDto(shoppingCartEntity,products);
    }

    @Override
    public ShoppingCartResponseDto getShoppingCartByIdAndUserId(Long id, Long userId) {
        return null;
    }

    @Override
    public ShoppingCartResponseDto updateShoppingCart(Long id, ShoppingCartRequestDto shoppingCartRequestDto) {
        return null;
    }

    @Override
    public ShoppingCartResponseDto deleteShoppingCart(Long id, Long userId) {
        return null;
    }

    @Override
    public ShoppingCartResponseDto getShoppingCart(Long id) {
        return null;
    }
}
