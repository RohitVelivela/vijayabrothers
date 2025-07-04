package com.vijaybrothers.store.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    // ─── Primary key ───
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    // ─── Business columns ───
    @Column(name = "product_code", nullable = false, unique = true)
    private String productCode;

    private String name;

    @Column(unique = true)
    private String slug;

    @Column(length = 2000)
    private String description;

    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductImage> images;

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    private List<ProductImage> galleryImages = new ArrayList<>();

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "is_in_stock")
    private Boolean inStock;

    @Column(name = "youtube_link")
    private String youtubeLink;

    @Column(name = "main_image_url")
    private String mainImageUrl;

    // ─── Product attributes ───
    private String color;

    private String fabric;

    // ─── Audit ───
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;
}
