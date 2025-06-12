package com.example.demo.payment;

import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

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
        paymentRequestDTO.creditorBic());

    given(this.paymentService.createPayment(any(PaymentRequestDTO.class)))
        .willReturn(paymentResponseDTO);

    this.mockMvc
        .perform(post("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(paymentRequestDTO)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", aMapWithSize(8)))
        .andExpect(jsonPath("id", equalTo((int) paymentResponseDTO.id())))
        .andExpect(jsonPath("type", equalTo(paymentResponseDTO.type().toString())))
        .andExpect(jsonPath("amount", equalTo(paymentResponseDTO.amount().doubleValue())))
        .andExpect(jsonPath("currency", equalTo(paymentResponseDTO.currency())))
        .andExpect(jsonPath("debtorIban", equalTo(paymentResponseDTO.debtorIban())))
        .andExpect(jsonPath("creditorIban", equalTo(paymentResponseDTO.creditorIban())))
        .andExpect(jsonPath("details", equalTo(paymentResponseDTO.details())))
        .andExpect(jsonPath("creditorBic", equalTo(paymentResponseDTO.creditorBic())));

    then(this.paymentService).should(times(1)).createPayment(any(PaymentRequestDTO.class));
  }
}
