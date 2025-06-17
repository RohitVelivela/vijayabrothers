package com.vijaybrothers.store.dto;

import java.time.Instant;

public record BannerDto(
    Integer bannerId,
    String  imageUrl,
    String  linkTo,
    Instant createdAt
) {}