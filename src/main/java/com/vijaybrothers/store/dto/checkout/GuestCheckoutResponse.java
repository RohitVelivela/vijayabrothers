package com.vijaybrothers.store.dto.checkout;

public record GuestCheckoutResponse(
    Integer orderId,
    String orderNumber,
    String paymentSessionUrl
) {}
