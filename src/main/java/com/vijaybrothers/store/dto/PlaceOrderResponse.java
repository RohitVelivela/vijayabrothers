package com.vijaybrothers.store.dto;

import java.math.BigDecimal;

public record PlaceOrderResponse(
    Long orderId,
    String orderNumber,
    BigDecimal totalAmount,
    String status
) {}
