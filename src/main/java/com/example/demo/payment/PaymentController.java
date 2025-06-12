package com.example.demo.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PaymentController {

  private final PaymentService paymentService;

  @Autowired
  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping("/payments")
  public ResponseEntity<PaymentResponseDTO> createPayment(
      @RequestBody PaymentRequestDTO paymentRequestDTO) {
    PaymentResponseDTO paymentResponseDTO = this.paymentService.createPayment(paymentRequestDTO);

    return ResponseEntity.ok(paymentResponseDTO);
  }

}
