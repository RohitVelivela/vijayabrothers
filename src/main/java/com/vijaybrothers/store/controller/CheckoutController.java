package com.vijaybrothers.store.controller;

import com.vijaybrothers.store.dto.checkout.GuestCheckoutRequest;
import com.vijaybrothers.store.dto.checkout.GuestCheckoutResponse;
import com.vijaybrothers.store.dto.GuestInfoDto;
import com.vijaybrothers.store.service.CheckoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    /**
     * POST /api/checkout/guest
     * Place an order for a guest (no login required).
     */
    @PostMapping("/guest")
    @ResponseStatus(HttpStatus.CREATED)
    public GuestCheckoutResponse guestCheckout(
        @Valid @RequestBody GuestCheckoutRequest req
    ) {
        return checkoutService.guestCheckout(req);
    }

    /**
     * GET /api/checkout/guest/{guestId}
     * Retrieve stored guest details
     */
    @GetMapping("/guest/{guestId}")
    public ResponseEntity<?> getGuestInfo(@PathVariable Integer guestId) {
        try {
            GuestInfoDto dto = checkoutService.getGuestInfo(guestId);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity
                .status(404)
                .body(Map.of("error", ex.getMessage()));
        }
    }
}
