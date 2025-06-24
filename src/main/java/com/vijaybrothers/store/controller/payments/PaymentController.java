package com.vijaybrothers.store.controller.payments;

import com.vijaybrothers.store.dto.*;
import com.vijaybrothers.store.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService svc;

    @Autowired
    public PaymentController(PaymentService svc) {
        this.svc = svc;
    }

   @PostMapping("/create")
    public ResponseEntity<PlaceOrderResponse> createOrder(@RequestBody PaymentCreateRequest req) {
    try {
        PlaceOrderResponse response = svc.createOrder(req);
        return ResponseEntity.ok(response);
    } catch (Exception e) {
        e.printStackTrace();  // TEMP: Log error
        return ResponseEntity.badRequest().body(null);
    }
}


   
    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentDetailDto> getDetails(@PathVariable Long orderId) {
        PaymentDetailDto dto = svc.fetchDetails(orderId);
        return ResponseEntity.ok(dto);
    }
}
