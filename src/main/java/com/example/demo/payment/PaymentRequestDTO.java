package com.example.demo.payment;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PaymentRequestDTO(
    @NotNull PaymentType type,
    @NotNull @DecimalMin(value = "0.01") BigDecimal amount,
    @NotNull @Pattern(regexp = "^(EUR|USD)$") String currency,
    @NotNull String debtorIban,
    @NotNull String creditorIban,
    String details,
    String creditorBic) {

}
