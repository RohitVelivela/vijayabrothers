package com.vijaybrothers.store.dto;

import com.vijaybrothers.store.model.Product;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductDto {
    private Integer   productId;
    private String    productCode;
    private String    name;
    private String    description;
    private BigDecimal price;
    private Integer   categoryId;
    private Integer   stockQuantity;
    private Boolean   inStock;
    private String    youtubeLink;
    private Instant   createdAt;

    public static ProductDto from(Product p) {
        return new ProductDto(
            p.getProductId(),
            p.getProductCode(),
            p.getName(),
            p.getDescription(),
            p.getPrice(),
            p.getCategory().getCategoryId(),
            p.getStockQuantity(),
            p.getInStock(),
            p.getYoutubeLink(),
            p.getCreatedAt()
        );
    }
}