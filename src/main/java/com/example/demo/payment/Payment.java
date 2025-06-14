package com.example.demo.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private PaymentType type;

  private BigDecimal amount;
  private String currency;
  private String debtorIban;
  private String creditorIban;
  private String details;
  private String creditorBic;
  private LocalDateTime createdAt;
  private LocalDateTime cancelledAt;
  private BigDecimal cancellationFee;
  private boolean cancelled;

  public Payment() {

  }

  public Payment(PaymentType type, BigDecimal amount, String currency, String debtorIban,
      String creditorIban, String details, String creditorBic) {
    this.type = type;
    this.amount = amount;
    this.currency = currency;
    this.debtorIban = debtorIban;
    this.creditorIban = creditorIban;
    this.details = details;
    this.creditorBic = creditorBic;
    // Truncate to remove nanoseconds
    this.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    this.cancelled = false;
  }

  public PaymentType getType() {
    return type;
  }

  public void setType(PaymentType type) {
    this.type = type;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getDebtorIban() {
    return debtorIban;
  }

  public void setDebtorIban(String debtorIban) {
    this.debtorIban = debtorIban;
  }

  public String getCreditorIban() {
    return creditorIban;
  }

  public void setCreditorIban(String creditorIban) {
    this.creditorIban = creditorIban;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public String getCreditorBic() {
    return creditorBic;
  }

  public void setCreditorBic(String creditorBic) {
    this.creditorBic = creditorBic;
  }

  public Long getId() {
    return id;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getCancelledAt() {
    return cancelledAt;
  }

  public void setCancelledAt(LocalDateTime cancelledAt) {
    this.cancelledAt = cancelledAt;
  }

  public BigDecimal getCancellationFee() {
    return cancellationFee;
  }

  public void setCancellationFee(BigDecimal cancellationFee) {
    this.cancellationFee = cancellationFee;
  }

  public boolean isCancelled() {
    return cancelled;
  }

  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }

}
