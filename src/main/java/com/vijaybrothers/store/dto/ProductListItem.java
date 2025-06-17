package com.vijaybrothers.store.dto;

import java.math.BigDecimal;

public record ProductListItem(
        Integer productId,
        String  productCode,
        String  name,
        BigDecimal price,
        Boolean inStock,
        String  youtubeLink,
        String  categoryName
) {}
