package com.vijaybrothers.store.controller;

import com.vijaybrothers.store.dto.CategoryCreateRequest;
import com.vijaybrothers.store.dto.CategoryProductAssignRequest;
import com.vijaybrothers.store.dto.CategoryDto; // Added
import com.vijaybrothers.store.dto.CategoryRequest; // Added
import com.vijaybrothers.store.service.AdminCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Added
import org.springframework.web.bind.annotation.*;

import java.util.Map; // Already present

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final AdminCategoryService service;

    /**
     * Creates a new product category
     * POST /api/admin/categories
     *
     * @param req The category details
     * @return 201 CREATED with success message, or 400 BAD REQUEST if validation fails
     */
    @PostMapping
    public ResponseEntity<Map<String, String>> createCategory(
            @Valid @RequestBody CategoryCreateRequest req
    ) {
        try {
            service.createCategory(req);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("message", "Category is created successfully"));
        // DEVELOPER NOTE: This catch block for IllegalArgumentException returns a 400 Bad Request.
        // This differs from the GlobalExceptionHandler which returns a 404 for IllegalArgumentException.
        // Consider using a more specific custom exception from the service layer if 400 is the desired response for certain business rule violations not caught by @Valid.
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                .badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Assigns multiple products to a category in bulk
     * POST /api/admin/categories/{categoryId}/products
     *
     * @param categoryId The ID of the category to assign products to
     * @param req Request containing list of product IDs
     * @return 200 OK with success message, or error response
     */
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<Map<String, String>> assignProducts(
            @PathVariable Integer categoryId,
            @Valid @RequestBody CategoryProductAssignRequest req
    ) {
        try {
            service.assignProductsToCategory(categoryId, req);
            return ResponseEntity.ok(Map.of("message", "Products assigned to category successfully"));
        // DEVELOPER NOTE: This catch block uses specific messages from IllegalArgumentException to differentiate between 404 and 400.
        // Consider using distinct custom exceptions from the service layer (e.g., CategoryNotFoundException, InvalidProductAssignmentException)
        // and handling them in GlobalExceptionHandler for cleaner controller logic.
        } catch (IllegalArgumentException e) {
            String error = e.getMessage();
            HttpStatus status = error.equals("Category not found") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
            return ResponseEntity
                .status(status)
                .body(Map.of("error", error));
        }
    }

    // Moved from CategoryController - for admin use
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Using simple name
    public ResponseEntity<?> updateCategory(
        @PathVariable Integer id,
        @Valid @RequestBody CategoryRequest req // Using simple name
    ) {
        try {
            // Assuming AdminCategoryService 'service' has or will have an 'update' method.
            // If it returns a DTO, capture it. For now, match original structure.
            CategoryDto updated = service.updateCategory(id, req); // Using simple name
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            // Consistent with GlobalExceptionHandler for IllegalArgumentException (404 with body)
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND) // Using imported HttpStatus
                .body(Map.of("error", e.getMessage())); // Using imported Map
        }
    }
}
