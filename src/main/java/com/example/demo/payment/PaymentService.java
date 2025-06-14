package com.example.demo.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

  private final PaymentRepository paymentRepository;

  @Autowired
  public PaymentService(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  public PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequestDTO) {
    Payment payment = PaymentMapper.toEntity(paymentRequestDTO);

    Payment savedPayment = this.paymentRepository.save(payment);

    return PaymentMapper.toDTO(savedPayment);
  }
}
