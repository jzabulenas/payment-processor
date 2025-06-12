package com.example.demo.payment;

import java.math.BigDecimal;

public record PaymentResponseDTO(long id,
    PaymentType type,
    BigDecimal amount,
    String currency,
    String debtorIban,
    String creditorIban,
    String details,
    String creditorBic) {

}
