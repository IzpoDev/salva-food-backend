package com.salvafood.api.salvafood_api.service.impl;

import com.salvafood.api.salvafood_api.exception.EntityNotFoundException;
import com.salvafood.api.salvafood_api.mapper.PaymentMapper;
import com.salvafood.api.salvafood_api.model.dto.PaymentRequestDto;
import com.salvafood.api.salvafood_api.model.dto.PaymentResponseDto;
import com.salvafood.api.salvafood_api.model.entity.PaymentEntity;
import com.salvafood.api.salvafood_api.model.entity.ShoppingCartEntity;
import com.salvafood.api.salvafood_api.model.entity.UserEntity;
import com.salvafood.api.salvafood_api.repository.*;
import com.salvafood.api.salvafood_api.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PurchaseDetailRepository purchaseDetailRepository;

    @Override
    public PaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setType(paymentRequestDto.getType());
        ShoppingCartEntity shoppingCartEntity = shoppingCartRepository.findById(paymentRequestDto.getShoppingCartId())
                .orElseThrow(() -> new EntityNotFoundException("Carrito de compras no encontrado"));
        UserEntity userEntity = userRepository.findById(shoppingCartEntity.getUser().getId()).orElseThrow(
                () -> new EntityNotFoundException("Usuario no encontrado")
        );
        paymentEntity.setShoppingCart(shoppingCartEntity);
        paymentEntity.setActive(Boolean.TRUE);
        paymentEntity.setCodigo(generateCodigoUnique(shoppingCartEntity.getId(),userEntity.getId()));
        return PaymentMapper.toDto(paymentRepository.save(paymentEntity), shoppingCartEntity.getTotal());
    }
    private String generateCodigoUnique(Long shoopingcartid, Long userid) {
        return String.valueOf(shoopingcartid + userid + Math.random());

    }
}
