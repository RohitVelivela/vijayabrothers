package com.vijaybrothers.store.controller;

import com.vijaybrothers.store.dto.OrderCreateRequest;
import com.vijaybrothers.store.dto.OrderSummaryDto;
import com.vijaybrothers.store.dto.PlaceOrderResponse;
import com.vijaybrothers.store.dto.OrderTrackDto;
import com.vijaybrothers.store.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService svc;

    @PostMapping
    public ResponseEntity<PlaceOrderResponse> placeOrder(@Valid @RequestBody OrderCreateRequest req) {
        try {
            PlaceOrderResponse response = svc.createOrder(req);
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/guest/{guestId}")
    public Page<OrderSummaryDto> getGuestOrders(@PathVariable String guestId, Pageable pageable) {
        return svc.getGuestOrders(guestId, pageable);
    }

    /**
     * GET /api/orders/track/{orderId}
     */
    @GetMapping("/track/{orderId}")
    public ResponseEntity<?> trackOrder(@PathVariable Integer orderId) {
        try {
            OrderTrackDto dto = svc.trackOrder(orderId);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity
                .status(404)
                .body(Map.of("error", ex.getMessage()));
        }
    }
}
