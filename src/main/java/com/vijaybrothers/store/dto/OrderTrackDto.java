package com.vijaybrothers.store.dto;

import java.time.Instant;

public record OrderTrackDto(
    String orderNumber,
    String orderStatus,
    String paymentStatus,
    Instant updatedAt
) {}
