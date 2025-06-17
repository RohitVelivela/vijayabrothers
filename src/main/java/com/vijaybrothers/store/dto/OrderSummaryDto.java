package com.vijaybrothers.store.dto;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class OrderSummaryDto {    private String orderNumber;
    private BigDecimal totalAmount;
    private String orderStatus;    // Changed from OrderStatus enum to String
    private String paymentStatus;  // Changed from PaymentStatus enum to String
    private Instant createdAt;
}
