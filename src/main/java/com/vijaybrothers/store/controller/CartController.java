package com.vijaybrothers.store.controller;

import com.vijaybrothers.store.dto.cart.CartItemRequest;
import com.vijaybrothers.store.dto.cart.CartUpdateRequest;
import com.vijaybrothers.store.dto.cart.CartView;
import com.vijaybrothers.store.service.CartService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/items")    public ResponseEntity<CartView> addToCart(
        @CookieValue(name = "cartId", required = false) String cartId,
        @Valid @RequestBody CartItemRequest request
    ) {
        CartView cartView = cartService.addItem(cartId, request);
        return ResponseEntity.ok(cartView);
    }

    /**
     * GET /api/cart
     * Return the full cart for the given cartId (cookie), or an empty one if none.
     */
    @GetMapping
    public CartView viewCart(
        @CookieValue(name = "cartId", required = false) String cartIdCookie,
        HttpServletResponse response
    ) {
        // If no cookie, create a new cartId and set it
        String cartId = (cartIdCookie == null || cartIdCookie.isBlank())
            ? UUID.randomUUID().toString()
            : cartIdCookie;

        if (!cartId.equals(cartIdCookie)) {
            Cookie c = new Cookie("cartId", cartId);
            c.setPath("/");
            c.setHttpOnly(true);
            c.setMaxAge(7 * 24 * 60 * 60); // 1 week
            response.addCookie(c);
        }

        // Build and return the cart snapshot (empty if no lines)
        return cartService.buildView(cartId);
    }

    /**
     * PUT /api/cart/items/{itemId}
     * Update the quantity of an existing cart line.
     * If quantity == 0, the line is removed.
     */
    @PutMapping("/items/{itemId}")
    public CartView updateItem(
        @CookieValue(name = "cartId", required = false) String cartIdCookie,
        @PathVariable Integer itemId,
        @Valid @RequestBody CartUpdateRequest req,
        HttpServletResponse response
    ) {
        // ensure cartId cookie
        String cartId = (cartIdCookie == null || cartIdCookie.isBlank())
            ? UUID.randomUUID().toString()
            : cartIdCookie;
        if (!cartId.equals(cartIdCookie)) {
            Cookie c = new Cookie("cartId", cartId);
            c.setPath("/");
            c.setHttpOnly(true);
            c.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(c);
        }

        // update (or delete) and return the updated cart
        return cartService.updateQty(itemId, req.quantity());
    }

    /**
     * DELETE /api/cart/items/{itemId}
     * Remove a cart line outright.
     */
    @DeleteMapping("/items/{itemId}")
    public CartView deleteItem(
        @CookieValue(name = "cartId", required = false) String cartIdCookie,
        @PathVariable Integer itemId,
        HttpServletResponse response
    ) {
        // same cookie logic
        String cartId = (cartIdCookie == null || cartIdCookie.isBlank())
            ? UUID.randomUUID().toString()
            : cartIdCookie;
        if (!cartId.equals(cartIdCookie)) {
            Cookie c = new Cookie("cartId", cartId);
            c.setPath("/");
            c.setHttpOnly(true);
            c.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(c);
        }

        // deleting is just setting qty to 0
        return cartService.updateQty(itemId, 0);
    }
}
