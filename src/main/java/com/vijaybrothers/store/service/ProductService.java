package com.vijaybrothers.store.service;

import com.vijaybrothers.store.dto.ProductDetailDto;
import com.vijaybrothers.store.dto.ProductDto;
import com.vijaybrothers.store.model.Product;
import com.vijaybrothers.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepo;

    /**
     * Get detailed information about a specific product
     * 
     * @param productId The ID of the product to retrieve
     * @return Complete product details as DTO
     * @throws IllegalArgumentException if product not found
     */
    @Transactional(readOnly = true)
    public ProductDetailDto getProductById(Integer productId) {
        Product product = productRepo.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        return new ProductDetailDto(
            product.getProductId(),
            product.getProductCode(),
            product.getName(),
            product.getDescription(),
            product.getMainImageUrl(),            
            product.getGalleryImages().stream()
                .map(img -> img.getImageUrl())
                .collect(Collectors.toList()),
            product.getPrice(),
            product.getInStock(),
            product.getStockQuantity(),
            product.getYoutubeLink(),
            product.getCategory() != null
                ? new ProductDetailDto.CategoryInfo(
                    product.getCategory().getCategoryId(),
                    product.getCategory().getName())
                : null,
            product.getCreatedAt(),
            product.getUpdatedAt()
        );
    }

    /** 1️⃣ Get by SKU */
    public ProductDto getBySku(String sku) {
        Product p = productRepo.findByProductCode(sku)
            .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return ProductDto.from(p);
    }

    /** 2️⃣ Search name/code with paging */
    public Page<ProductDto> search(String q, Pageable page) {
        return productRepo
            .findByNameContainingIgnoreCaseOrProductCodeContainingIgnoreCase(q, q, page)
            .map(ProductDto::from);
    }

    /** 3️⃣ List all in-stock */
    public List<ProductDto> inStock() {
        return productRepo.findByInStockTrue()
            .stream().map(ProductDto::from).toList();
    }

    /** 4️⃣ List low-stock using threshold from application.properties */
    @Value("${app.lowStockThreshold:5}")
    private int lowStockThreshold;

    public List<ProductDto> lowStock() {
        return productRepo.findByStockQuantityLessThan(lowStockThreshold)
            .stream().map(ProductDto::from).toList();
    }
}
