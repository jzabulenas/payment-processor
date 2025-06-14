package com.example.demo.payment;

public class PaymentMapper {

  public static Payment toEntity(PaymentRequestDTO paymentRequestDTO) {
    return new Payment(paymentRequestDTO.type(),
        paymentRequestDTO.amount(),
        paymentRequestDTO.currency(),
        paymentRequestDTO.debtorIban(),
        paymentRequestDTO.creditorIban(),
        paymentRequestDTO.details(),
        paymentRequestDTO.creditorBic());
  }

  public static PaymentResponseDTO toDTO(Payment payment) {
    return new PaymentResponseDTO(payment.getId(),
        payment.getType(),
        payment.getAmount(),
        payment.getCurrency(),
        payment.getDebtorIban(),
        payment.getCreditorIban(),
        payment.getDetails(),
        payment.getCreditorBic(),
        payment.getCreatedAt(),
        payment.getCancelledAt(),
        payment.getCancellationFee(),
        payment.isCancelled());
  }
}
