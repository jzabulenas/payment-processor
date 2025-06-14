package com.example.demo.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

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
      @RequestBody @Valid PaymentRequestDTO paymentRequestDTO) {
    PaymentResponseDTO paymentResponseDTO = this.paymentService.createPayment(paymentRequestDTO);

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(paymentResponseDTO.id())
        .toUri())
        .body(paymentResponseDTO);
  }

  @PutMapping("/payments/{id}/cancel")
  public ResponseEntity<PaymentResponseDTO> cancelPayment(@PathVariable long id) {
    PaymentResponseDTO paymentResponseDTO = this.paymentService.cancelPayment(id);

    return ResponseEntity.ok(paymentResponseDTO);
  }
}
