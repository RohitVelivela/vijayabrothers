package com.vijaybrothers.store.controller;

import com.vijaybrothers.store.dto.*; // Assuming ProductSummaryDto is here
import com.vijaybrothers.store.service.CategoryService;
// CategoryRequest and CategoryDto are effectively removed if not used by other methods via the wildcard
import jakarta.validation.Valid; // This was not in the original snippet, but likely needed if any other @Valid exists
import lombok.RequiredArgsConstructor;
import org.springframework.http.*; // HttpStatus was used by the deleted method, may not be needed if getProductsByCategory doesn't use it explicitly
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map; // Map was used by the deleted method's error response, and also by getProductsByCategory

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

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
                .status(404) // Explicit status, so HttpStatus import might still be good if this was HttpStatus.NOT_FOUND
                .body(Map.of("error", e.getMessage()));
        }
    }
}
