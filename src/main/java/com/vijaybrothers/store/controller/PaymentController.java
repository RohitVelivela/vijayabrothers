package com.vijaybrothers.store.controller;

import com.vijaybrothers.store.dto.PaymentCreateRequest;
import com.vijaybrothers.store.dto.PaymentDetailDto;
import com.vijaybrothers.store.service.PaymentService;
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
}
