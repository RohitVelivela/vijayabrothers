package com.vijaybrothers.store.controller.payments;

import com.vijaybrothers.store.dto.payments.*;
import com.vijaybrothers.store.service.payments.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService svc;

    public PaymentController(PaymentService svc) {
        this.svc = svc;
    }

    @PostMapping("/create-order")
    public ResponseEntity<PlaceOrderResponse> createOrder(
            @Validated @RequestBody PaymentCreateRequest req
    ) throws RazorpayException {
        return ResponseEntity.ok(svc.createOrder(req));
    }

    @PostMapping("/verify")
    public ResponseEntity<PaymentVerifyResponse> verifyPayment(
            @Validated @RequestBody PaymentVerifyRequest req
    ) {
        return ResponseEntity.ok(svc.verifyAndSave(req));
    }

    @GetMapping("/order-status/{orderId}")
    public ResponseEntity<PaymentDetailDto> getStatus(@PathVariable String orderId) {
        return ResponseEntity.ok(svc.fetchDetails(orderId));
    }
}
