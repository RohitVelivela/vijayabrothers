package com.vijaybrothers.store.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * DTO for detailed product information
 * Used in GET /api/products/{productId}
 */
public record ProductDetailDto(
    Integer productId,
    String productCode,
    String name,
    String description,
    String mainImageUrl,
    List<String> galleryImageUrls,
    BigDecimal price,
    Boolean inStock,
    Integer stockQuantity,
    String youtubeLink,
    CategoryInfo category,
    Instant createdAt,
    Instant updatedAt
) {
    /**
     * Nested record for category information in product details
     */
    public record CategoryInfo(
        Integer categoryId,
        String name
    ) {}
}
