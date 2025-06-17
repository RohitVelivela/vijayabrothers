package com.vijaybrothers.store.controller;

import com.vijaybrothers.store.dto.*;
import com.vijaybrothers.store.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
        @PathVariable Integer id,
        @Valid @RequestBody CategoryRequest req
    ) {
        try {
            CategoryDto updated = service.update(id, req);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Get all products in a specific category
     * GET /api/categories/{categoryId}/products
     * 
     * @param categoryId The ID of the category to get products from
     * @return List of products or error response
     */
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<?> getProductsByCategory(@PathVariable Integer categoryId) {
        try {
            List<ProductSummaryDto> products = service.getProductsByCategory(categoryId);
            return ResponseEntity.ok(products);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                .status(404)
                .body(Map.of("error", e.getMessage()));
        }
    }
}
