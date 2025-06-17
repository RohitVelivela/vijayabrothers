package com.vijaybrothers.store.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record PaymentCreateRequest(
    @NotNull     Integer     orderId,
    @NotNull     BigDecimal  amount,
    @NotBlank    String      method,
    @NotBlank    String      transactionId,
    @NotBlank    String      status         // PAID, FAILED, REFUNDED
) {}
