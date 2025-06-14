package com.example.demo.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

      break;
    }

    Payment payment = PaymentMapper.toEntity(paymentRequestDTO);

    Payment savedPayment = this.paymentRepository.save(payment);

    return PaymentMapper.toDTO(savedPayment);
  }
}
