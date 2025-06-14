package com.example.demo.payment;

import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = PaymentController.class)
public class PaymentControllerTest {

  @Autowired
  MockMvc mockMvc;
  @MockitoBean
  PaymentService paymentService;

  // createPayment
  //
  //
  //
  //
  //
  //
  //
  //
  //

  @Test
  void createPayment_whenCreatePayment_thenReturnBodyAnd201()
      throws JsonProcessingException, Exception {
    PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(PaymentType.TYPE1,
        BigDecimal.valueOf(100.50),
        "EUR",
        "DE89370400440532013000",
        "FR1420041010050500013M02606",
        "Invoice payment",
        null);

    PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO(0,
        paymentRequestDTO.type(),
        paymentRequestDTO.amount(),
        paymentRequestDTO.currency(),
        paymentRequestDTO.debtorIban(),
        paymentRequestDTO.creditorIban(),
        paymentRequestDTO.details(),
        paymentRequestDTO.creditorBic(),
        LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
        null,
        null,
        false);

    given(this.paymentService.createPayment(any(PaymentRequestDTO.class)))
        .willReturn(paymentResponseDTO);
    this.mockMvc
        .perform(post("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(paymentRequestDTO)))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", aMapWithSize(12)))
        .andExpect(jsonPath("id", equalTo((int) paymentResponseDTO.id())))
        .andExpect(jsonPath("type", equalTo(paymentResponseDTO.type().toString())))
        .andExpect(jsonPath("amount", equalTo(paymentResponseDTO.amount().doubleValue())))
        .andExpect(jsonPath("currency", equalTo(paymentResponseDTO.currency())))
        .andExpect(jsonPath("debtorIban", equalTo(paymentResponseDTO.debtorIban())))
        .andExpect(jsonPath("creditorIban", equalTo(paymentResponseDTO.creditorIban())))
        .andExpect(jsonPath("details", equalTo(paymentResponseDTO.details())))
        .andExpect(jsonPath("creditorBic", equalTo(paymentResponseDTO.creditorBic())))
        .andExpect(jsonPath("createdAt", equalTo(paymentResponseDTO.createdAt().toString())))
        .andExpect(jsonPath("cancelledAt", equalTo(null)))
        .andExpect(jsonPath("cancellationFee", equalTo(null)))
        .andExpect(jsonPath("cancelled", equalTo(false)))
        .andExpect(header().string("Location", containsString("/api/payments/0")));

    then(this.paymentService).should(times(1)).createPayment(any(PaymentRequestDTO.class));
  }

  // cancelPayment
  //
  //
  //
  //
  //
  //
  //
  //
  //

  @Test
  void cancelPayment_whenCancelPayment_thenReturnBodyAnd200() throws Exception {
    long paymentId = 0L;
    LocalDateTime cancelledAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    BigDecimal cancellationFee = BigDecimal.valueOf(0.05);

    PaymentResponseDTO cancelledPayment = new PaymentResponseDTO(
        paymentId,
        PaymentType.TYPE1,
        BigDecimal.valueOf(100.50),
        "EUR",
        "DE89370400440532013000",
        "FR1420041010050500013M02606",
        "Invoice payment",
        null,
        LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
        cancelledAt,
        cancellationFee,
        true);

    given(this.paymentService.cancelPayment(paymentId))
        .willReturn(cancelledPayment);

    this.mockMvc
        .perform(put("/api/payments/{id}/cancel", paymentId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", aMapWithSize(12)))
        .andExpect(jsonPath("id", equalTo((int) cancelledPayment.id())))
        .andExpect(jsonPath("type", equalTo(cancelledPayment.type().toString())))
        .andExpect(jsonPath("amount", equalTo(cancelledPayment.amount().doubleValue())))
        .andExpect(jsonPath("currency", equalTo(cancelledPayment.currency())))
        .andExpect(jsonPath("debtorIban", equalTo(cancelledPayment.debtorIban())))
        .andExpect(jsonPath("creditorIban", equalTo(cancelledPayment.creditorIban())))
        .andExpect(jsonPath("details", equalTo(cancelledPayment.details())))
        .andExpect(jsonPath("creditorBic", equalTo(cancelledPayment.creditorBic())))
        .andExpect(jsonPath("createdAt", equalTo(cancelledPayment.createdAt().toString())))
        .andExpect(jsonPath("cancelledAt", equalTo(cancelledPayment.cancelledAt().toString())))
        .andExpect(jsonPath("cancellationFee", equalTo(cancelledPayment.cancellationFee()
            .doubleValue())))
        .andExpect(jsonPath("cancelled", equalTo(true)));

    then(this.paymentService).should(times(1)).cancelPayment(paymentId);
  }

}
