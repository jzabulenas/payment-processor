package com.example.demo.payment;

import java.math.BigDecimal;

public record PaymentSpecificDTO(long id,
    BigDecimal cancellationFee) {
}
