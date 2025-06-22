package com.vijaybrothers.store.controller;

import com.razorpay.RazorpayException;
import com.vijaybrothers.store.dto.PaymentCreateRequest;
import com.vijaybrothers.store.dto.PaymentDetailDto;
import com.vijaybrothers.store.service.PaymentService;
import com.vijaybrothers.store.service.RazorpayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService svc;
    private final RazorpayService razorpayService;

    @PostMapping
    public ResponseEntity<?> recordPayment(@Valid @RequestBody PaymentCreateRequest req) {
        try {
            svc.recordPayment(req);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("message", "Payment recorded successfully"));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity
                .badRequest()
                .body(Map.of("error", ex.getMessage()));
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getPaymentByOrder(@PathVariable Integer orderId) {
        try {
            PaymentDetailDto dto = svc.getPaymentByOrder(orderId);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(404).body(Map.of("error", ex.getMessage()));
        }
    }

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(
            @RequestParam int amount,
            @RequestParam(defaultValue = "INR") String currency) {
        try {
            String orderId = razorpayService.createOrder(amount, currency, "order_" + System.currentTimeMillis());
            return ResponseEntity.ok(orderId);
        } catch (RazorpayException e) {
            return ResponseEntity.badRequest().body("Error creating payment order: " + e.getMessage());
        }
    }
}
