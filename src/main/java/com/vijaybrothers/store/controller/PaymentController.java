package com.vijaybrothers.store.controller;

import com.vijaybrothers.store.dto.PaymentCreateRequest;
import com.vijaybrothers.store.dto.PaymentDetailDto;
import com.vijaybrothers.store.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// DEVELOPER NOTE: The security for endpoints in this controller needs review.
// Check SecurityConfig.java for current rules. Determine if these routes should be admin-only,
// part of a guest checkout flow (and thus permitAll or tied to a guest session/order ID),
// or require other specific authorization.
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
        // DEVELOPER NOTE: This catch block for IllegalArgumentException returns a 400 Bad Request.
        // This differs from the GlobalExceptionHandler which returns a 404 for IllegalArgumentException.
        // Consider using a more specific custom exception from the service layer (e.g., InvalidPaymentDataException) if 400 is the desired response.
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
