package com.vijaybrothers.store.service;

import com.vijaybrothers.store.dto.ProductCreateRequest;
import com.vijaybrothers.store.dto.ProductDto;
import com.vijaybrothers.store.dto.ProductSummaryDto;
import com.vijaybrothers.store.dto.ProductUpdateRequest;
import com.vijaybrothers.store.model.Category;
import com.vijaybrothers.store.model.Product;
import com.vijaybrothers.store.repository.CategoryRepository;
import com.vijaybrothers.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProductService {
    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    /** Low-stock cutoff from properties (default 5) */
    @Value("${app.lowStockThreshold:5}")
    private int lowStockThreshold;

    /**
     * Creates a new product record.
     * Called by POST /api/admin/products
     */
    @Transactional
    public void createProduct(ProductCreateRequest req) {
        Category cat = categoryRepo.findById(req.categoryId())
            .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Product p = new Product();
        p.setProductCode(req.productCode());
        p.setName(req.name());
        p.setDescription(req.description());
        p.setPrice(req.price());
        p.setCategory(cat);
        p.setStockQuantity(req.stockQuantity());
        p.setInStock(req.inStock());
        p.setYoutubeLink(req.youtubeLink());
        p.setMainImageUrl(req.mainImageUrl());
        p.setCreatedAt(Instant.now());
        p.setUpdatedAt(Instant.now());
        // createdBy/updatedBy are set by your auditing filter if you have one

        productRepo.save(p);
    }

    /**
     * Get list of products that are under the low-stock threshold
     */
    @Transactional(readOnly = true)
    public List<ProductDto> lowStock() {
        return productRepo.findByStockQuantityLessThan(lowStockThreshold)
            .stream()
            .map(ProductDto::from)
            .collect(Collectors.toList());
    }

    /**
     * Fetch a summary list of all products.
     * Called by GET /api/admin/products
     */
    @Transactional(readOnly = true)
    public List<ProductSummaryDto> listProducts() {
        return productRepo.findAll().stream()            .map(p -> new ProductSummaryDto(
                p.getProductId(),
                p.getProductCode(),
                p.getName(),
                p.getMainImageUrl(),
                p.getPrice(),
                p.getInStock(),
                p.getCategory() != null ? p.getCategory().getCategoryId() : null
            ))
            .collect(Collectors.toList());
    }

    /**
     * Updates an existing product.
     * HTTP Method: PUT
     * Path: /api/admin/products/{productId}
     */
    @Transactional
    public void updateProduct(Integer productId, ProductUpdateRequest req) {
        Product p = productRepo.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Category cat = categoryRepo.findById(req.categoryId())
            .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        p.setProductCode(req.productCode());
        p.setName(req.name());
        p.setDescription(req.description());
        p.setPrice(req.price());
        p.setCategory(cat);
        p.setStockQuantity(req.stockQuantity());
        p.setInStock(req.inStock());
        p.setYoutubeLink(req.youtubeLink());
        p.setMainImageUrl(req.mainImageUrl());
        p.setUpdatedAt(Instant.now());

        productRepo.save(p);
    }

    /**
     * DELETE /api/admin/products/{productId}
     * Remove the product with the given ID.
     */
    @Transactional
    public void deleteProduct(Integer productId) {
        if (!productRepo.existsById(productId)) {
            throw new IllegalArgumentException("Product not found");
        }
        productRepo.deleteById(productId);
    }
}
