package com.vijaybrothers.store.controller;

import com.vijaybrothers.store.dto.ProductCreateRequest;
import com.vijaybrothers.store.dto.ProductDto;
import com.vijaybrothers.store.dto.ProductSummaryDto;
import com.vijaybrothers.store.dto.ProductUpdateRequest;
import com.vijaybrothers.store.service.AdminProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService svc;

    /**
     * GET /api/admin/products/low-stock
     * → Admin only: list products under low-stock threshold
     */
    @GetMapping("/low-stock")
    public ResponseEntity<List<ProductDto>> getLowStock() {
        List<ProductDto> list = svc.lowStock();
        return ResponseEntity.ok(list);
    }

    /**
     * POST /api/admin/products
     * Creates a new product in the catalog.
     * Returns a 201 with a success message.
     */
    @PostMapping
    public ResponseEntity<Map<String,String>> create(
        @Valid @RequestBody ProductCreateRequest req
    ) {
        svc.createProduct(req);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(Map.of("message", "Product is added successfully"));
    }

    /**
     * GET /api/admin/products
     * Returns a list of all products (summary).
     */
    @GetMapping
    public ResponseEntity<List<ProductSummaryDto>> listAll() {
        List<ProductSummaryDto> list = svc.listProducts();
        return ResponseEntity.ok(list);
    }

    /**
     * PUT /api/admin/products/{productId}
     * Updates an existing product.
     */
    @PutMapping("/{productId}")
    public ResponseEntity<Map<String,String>> update(
        @PathVariable Integer productId,
        @Valid @RequestBody ProductUpdateRequest req
    ) {
        svc.updateProduct(productId, req);
        return ResponseEntity
            .ok(Map.of("message", "Product is updated successfully"));
    }

    /**
     * DELETE /api/admin/products/{productId}
     * HTTP Method: DELETE
     * Deletes an existing product.
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<Map<String,String>> delete(
        @PathVariable Integer productId
    ) {
        svc.deleteProduct(productId);
        return ResponseEntity
            .ok(Map.of("message", "Product is deleted successfully"));
    }
}
