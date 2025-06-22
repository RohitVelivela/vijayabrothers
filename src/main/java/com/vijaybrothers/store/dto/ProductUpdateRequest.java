// src/main/java/com/vijaybrothers/store/dto/ProductUpdateRequest.java
package com.vijaybrothers.store.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {
    @NotBlank
    private String productCode;     // ← added so getProductCode() exists

    @NotBlank
    private String name;

    @Size(max = 2000)
    private String description;

    @NotNull @Positive
    private BigDecimal price;

    @NotNull
    private Integer categoryId;     // ← changed to Integer

    @NotNull @Min(0)
    private Integer stockQuantity;

    @NotNull
    private Boolean inStock;

    private String youtubeLink;

    @NotBlank
    private String mainImageUrl;
}
