package com.vijaybrothers.store.controller;

import com.vijaybrothers.store.dto.ProductListItem;
import com.vijaybrothers.store.service.ProductQueryService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Public product listing endpoints")
public class ProductPublicController {

    private final ProductQueryService svc;
    public ProductPublicController(ProductQueryService svc) { this.svc = svc; }

    /**
     * GET /api/products
     *
     * Query-params (all optional):
     *   page        default 0
     *   size        default 20
     *   categoryId  filter by category
     *   q           full-text search in product name
     */
    @GetMapping
    @Operation(summary = "List products with optional filtering and pagination")
    public Page<ProductListItem> list(
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false)    Integer categoryId,
            @RequestParam(required = false, value = "q") String search
    ) {
        return svc.list(categoryId, search, page, size);
    }
}
