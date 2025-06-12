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

  public Payment createPayment(PaymentRequestDTO paymentRequestDTO) {
    Payment payment = new Payment(
        paymentRequestDTO.type(),
        paymentRequestDTO.amount(),
        paymentRequestDTO.currency(),
        paymentRequestDTO.debtorIban(),
        paymentRequestDTO.creditorIban());

    payment.setDetails(paymentRequestDTO.details());
    payment.setCreditorBic(paymentRequestDTO.creditorBic());

    return this.paymentRepository.save(payment);
  }
}
