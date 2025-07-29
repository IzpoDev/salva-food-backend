package com.salvafood.api.salvafood_api.mapper;

import com.salvafood.api.salvafood_api.model.dto.PaymentRequestDto;
import com.salvafood.api.salvafood_api.model.dto.PaymentResponseDto;
import com.salvafood.api.salvafood_api.model.entity.PaymentEntity;

public class PaymentMapper {

    public static PaymentResponseDto toDto(PaymentEntity paymentEntity, Double paymentAmount) {
        PaymentResponseDto paymentResponseDto = new PaymentResponseDto();
        paymentResponseDto.setId(paymentEntity.getId());
        paymentResponseDto.setAmount(paymentAmount);
        paymentResponseDto.setType(paymentEntity.getType());
        paymentResponseDto.setCodigo(paymentEntity.getCodigo());
        paymentResponseDto.setShoppingCartId(paymentEntity.getShoppingCart().getId());
        return paymentResponseDto;
    }
}
