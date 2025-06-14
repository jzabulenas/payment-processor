package com.example.demo.exception;

public class PaymentCancellationException extends RuntimeException {
  public PaymentCancellationException(String message) {
    super(message);
  }
}
