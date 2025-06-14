package com.example.demo.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.PaymentCancellationException;
import com.example.demo.exception.PaymentNotFoundException;
import com.example.demo.exception.PaymentValidationException;

@Service
public class PaymentService {

  private final PaymentRepository paymentRepository;

  @Autowired
  public PaymentService(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  public PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequestDTO) {
    switch (paymentRequestDTO.type()) {
      case TYPE1:
        if (!"EUR".equals(paymentRequestDTO.currency())) {
          throw new PaymentValidationException("TYPE1 payments are only allowed for EUR");
        }

        if (paymentRequestDTO.details() == null) {
          throw new PaymentValidationException("Field 'details' is required for TYPE1 payments");
        }

        break;

      case TYPE2:
        if (!"USD".equals(paymentRequestDTO.currency())) {
          throw new PaymentValidationException("TYPE2 payments are only allowed for USD");
        }

        break;

      case TYPE3:
        if (!"EUR".equals(paymentRequestDTO.currency())
            && !"USD".equals(paymentRequestDTO.currency())) {
          throw new PaymentValidationException("TYPE3 payments are only allowed for EUR or USD");
        }

        if (paymentRequestDTO.creditorBic() == null) {
          throw new PaymentValidationException("TYPE3 payments require 'creditorBic' field");
        }

        break;
    }

    Payment payment = PaymentMapper.toEntity(paymentRequestDTO);

    Payment savedPayment = this.paymentRepository.save(payment);

    return PaymentMapper.toDTO(savedPayment);
  }

  public PaymentResponseDTO cancelPayment(long id) {
    Payment payment = paymentRepository.findById(id)
        .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));

    validateCancellationEligibility(payment);

    BigDecimal cancellationFee = calculateCancellationFee(payment);

    payment.setCancelled(true);
    payment.setCancelledAt(LocalDateTime.now());
    payment.setCancellationFee(cancellationFee);

    // Save update payment to the database
    Payment savedPayment = paymentRepository.save(payment);

    return PaymentMapper.toDTO(savedPayment);
  }

  private void validateCancellationEligibility(Payment payment) {
    if (payment.isCancelled()) {
      throw new PaymentCancellationException("Payment is already cancelled");
    }

    LocalDateTime now = LocalDateTime.now();
    // Creates yyyy-mm-ddT00:00
    LocalDateTime creationDate = payment.getCreatedAt().toLocalDate().atStartOfDay();
    LocalDateTime nextMidnight = creationDate.plusDays(1);

    if (now.isAfter(nextMidnight) || now.isEqual(nextMidnight)) {
      throw new PaymentCancellationException(
          "Payment can only be cancelled on the day of creation before 00:00");
    }
  }

  private BigDecimal calculateCancellationFee(Payment payment) {
    LocalDateTime createdAt = payment.getCreatedAt();
    LocalDateTime now = LocalDateTime.now();

    // Calculate full hours (2:59 = 2 hours)
    long fullHours = ChronoUnit.HOURS.between(createdAt, now);

    // Get coefficient based on payment type
    BigDecimal coefficient = getCancellationCoefficient(payment.getType());

    // Calculate fee: h * k (result in EUR)
    BigDecimal fee = BigDecimal.valueOf(fullHours).multiply(coefficient);

    return fee;
  }

  private BigDecimal getCancellationCoefficient(PaymentType type) {
    return switch (type) {
      case TYPE1 -> new BigDecimal("0.05");
      case TYPE2 -> new BigDecimal("0.10");
      case TYPE3 -> new BigDecimal("0.15");
    };
  }
}
