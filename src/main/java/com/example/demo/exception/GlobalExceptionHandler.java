package com.example.demo.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();

      errors.put(fieldName, errorMessage);
    });

    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(PaymentValidationException.class)
  public ResponseEntity<Map<String, String>> handlePaymentValidationException(
      PaymentValidationException ex) {

    Map<String, String> error = new HashMap<>();
    error.put("error", ex.getMessage());

    return ResponseEntity.badRequest().body(error);
  }

  @ExceptionHandler(PaymentNotFoundException.class)
  public ResponseEntity<Map<String, String>> handlePaymentNotFoundException(
      PaymentNotFoundException ex) {

    Map<String, String> error = new HashMap<>();
    error.put("error", ex.getMessage());

    return ResponseEntity.badRequest().body(error);
  }

  @ExceptionHandler(PaymentCancellationException.class)
  public ResponseEntity<Map<String, String>> handlePaymentCancellationException(
      PaymentCancellationException ex) {

    Map<String, String> error = new HashMap<>();
    error.put("error", ex.getMessage());

    return ResponseEntity.badRequest().body(error);
  }
}
