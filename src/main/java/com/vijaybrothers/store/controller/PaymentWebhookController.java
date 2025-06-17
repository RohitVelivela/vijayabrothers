package com.vijaybrothers.store.controller;

import com.vijaybrothers.store.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentWebhookController {

    private final PaymentService paymentService;

    /**
     * Receive payment gateway callbacks.
     * Gateway will POST JSON containing at least:
     *  - orderNumber (String)
     *  - status      (String; e.g. "COMPLETED","FAILED")
     */
    @PostMapping("/webhook")
    public ResponseEntity<Map<String,String>> handleWebhook(
        @RequestBody @Valid Map<String, Object> payload,
        @RequestHeader(name="X-Gateway-Signature", required=false) String signature
    ) {
        // TODO: verify signature if your gateway supports HMAC verification
        paymentService.processWebhook(payload);
        return ResponseEntity.ok(Map.of("status", "success"));
    }
}
