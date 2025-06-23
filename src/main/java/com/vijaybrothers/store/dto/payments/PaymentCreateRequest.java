package com.vijaybrothers.store.dto.payments;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PaymentCreateRequest {
    @NotNull
    @Min(1)
    private Long amount; // in paise

    @NotBlank
    private String currency; // e.g. "INR"

    @NotBlank
    private String receipt; // merchant’s receipt ID

    // getters & setters...
    public Long getAmount() { return amount; }
    public void setAmount(Long amount) { this.amount = amount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getReceipt() { return receipt; }
    public void setReceipt(String receipt) { this.receipt = receipt; }
}
