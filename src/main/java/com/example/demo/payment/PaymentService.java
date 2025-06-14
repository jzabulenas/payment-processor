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
}
