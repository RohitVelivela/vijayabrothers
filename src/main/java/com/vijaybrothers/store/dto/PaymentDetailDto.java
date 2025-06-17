package com.vijaybrothers.store.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentDetailDto(
    Long    paymentId,
    Long    orderId,
    BigDecimal amount,
    String     method,
    String     transactionId,
    String     status,
    Instant    createdAt
) {}
