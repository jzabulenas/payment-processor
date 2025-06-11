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

  private final PaymentRepository paymentRepository;

  @Autowired
  public PaymentController(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  @PostMapping("/payments")
  public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
    Payment savedPayment = this.paymentRepository.save(payment);

    return ResponseEntity.ok(savedPayment);
  }
}
