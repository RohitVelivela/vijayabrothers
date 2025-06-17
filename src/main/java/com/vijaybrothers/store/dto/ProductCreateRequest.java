package com.vijaybrothers.store.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;


public record ProductCreateRequest(
    @NotBlank String productCode,
    @NotBlank String name,
    @NotBlank String description,
    @NotNull @DecimalMin("0.0") BigDecimal price,
    @NotNull Integer categoryId,
    @NotNull @Min(0) Integer stockQuantity,
    @NotNull Boolean inStock,
    String youtubeLink,
    @NotBlank String mainImageUrl
) {}
