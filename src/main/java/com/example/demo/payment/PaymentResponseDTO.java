package com.example.demo.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponseDTO(long id,
    PaymentType type,
    BigDecimal amount,
    String currency,
    String debtorIban,
    String creditorIban,
    String details,
    String creditorBic,
    LocalDateTime createdAt,
    LocalDateTime cancelledAt,
    BigDecimal cancellationFee,
    boolean cancelled) {

}
