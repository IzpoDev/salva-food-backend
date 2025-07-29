package com.salvafood.api.salvafood_api.service;

import com.salvafood.api.salvafood_api.model.dto.PaymentRequestDto;
import com.salvafood.api.salvafood_api.model.dto.PaymentResponseDto;

public interface PaymentService {

    PaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto);
}
