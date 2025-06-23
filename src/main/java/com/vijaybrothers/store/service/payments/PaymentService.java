package com.vijaybrothers.store.service.payments;

import com.vijaybrothers.store.dto.payments.*;
// import com.vijaybrothers.store.model.payments.*;
import com.vijaybrothers.store.repository.payments.PaymentRepository;
import com.razorpay.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service("razorpayPaymentService")
public class PaymentService {
    private final RazorpayClient razorpayClient;
    private final PaymentRepository repo;
    private final String secret;

    public PaymentService(
            @Value("${razorpay.key_id}") String key,
            @Value("${razorpay.key_secret}") String secret,
            PaymentRepository repo
    ) throws RazorpayException {
        this.razorpayClient = new RazorpayClient(key, secret);
        this.repo = repo;
        this.secret = secret;
    }

    public PlaceOrderResponse createOrder(PaymentCreateRequest req) throws RazorpayException {
        JSONObject opts = new JSONObject()
                .put("amount", req.getAmount())
                .put("currency", req.getCurrency())
                .put("receipt", req.getReceipt());
        Order order = razorpayClient.orders.create(opts);
        return new PlaceOrderResponse(
                order.get("id"),
                order.get("currency"),
                order.get("amount"),
                order.get("status")
        );
    }

    public PaymentVerifyResponse verifyAndSave(PaymentVerifyRequest req) {
        // 1. Verify signature
        JSONObject attributes = new JSONObject();
        attributes.put("razorpay_order_id", req.getRazorpayOrderId());
        attributes.put("razorpay_payment_id", req.getRazorpayPaymentId());
        attributes.put("razorpay_signature", req.getRazorpaySignature());
        try {
            Utils.verifyPaymentSignature(attributes, secret);
        } catch (RazorpayException e) {
            throw new ResponseStatusException(BAD_REQUEST, "Invalid signature");
        }
        try {
            // 2. Fetch Razorpay payment details
            com.razorpay.Payment rzPay = razorpayClient.payments.fetch(req.getRazorpayPaymentId());
            Long amt = rzPay.get("amount");
            String method = rzPay.get("method");
            String rzStatus = rzPay.get("status"); // e.g. "captured"
            // 3. Map to our entity and save
            com.vijaybrothers.store.model.payments.Payment p = new com.vijaybrothers.store.model.payments.Payment();
            p.setOrderId(req.getRazorpayOrderId());
            p.setGateway("razorpay");
            p.setMethod(method);
            p.setStatus("captured".equalsIgnoreCase(rzStatus) ? com.vijaybrothers.store.model.payments.PaymentStatus.PAID : com.vijaybrothers.store.model.payments.PaymentStatus.FAILED);
            p.setTransactionId(req.getRazorpayPaymentId());
            p.setPaidAt(OffsetDateTime.ofInstant(Instant.ofEpochSecond(rzPay.get("created_at")), ZoneOffset.UTC));
            p.setAmount(amt);
            repo.save(p);
            return new PaymentVerifyResponse("Payment verified successfully", req.getRazorpayPaymentId());
        } catch (RazorpayException ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Error fetching payment details");
        }
    }

    public PaymentDetailDto fetchDetails(String orderId) {
        com.vijaybrothers.store.model.payments.Payment p = repo.findByOrderId(orderId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, "No payment found for orderId: " + orderId));
        return new PaymentDetailDto(
                p.getPaymentId(),
                orderId,
                p.getStatus().name(),
                p.getAmount(),
                p.getPaidAt()
        );
    }
}
