// src/main/java/com/vijaybrothers/store/service/PaymentService.java

package com.vijaybrothers.store.service;

import com.vijaybrothers.store.dto.payments.PaymentCreateRequest;
import com.vijaybrothers.store.dto.payments.PlaceOrderResponse;
import com.vijaybrothers.store.dto.payments.PaymentVerifyRequest;
import com.vijaybrothers.store.dto.payments.PaymentVerifyResponse;
import com.vijaybrothers.store.dto.payments.PaymentDetailDto;
import com.vijaybrothers.store.model.payments.Payment;
import com.vijaybrothers.store.model.payments.PaymentStatus;
import com.vijaybrothers.store.repository.payments.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final RazorpayClient razorpayClient;
    private final PaymentRepository paymentRepository;

    @Value("${razorpay.key_secret}")
    private String keySecret;

    /**
     * Create a Razorpay order session for the given receipt and amount.
     */
    public PlaceOrderResponse createPaymentSession(String receipt, BigDecimal amount) throws RazorpayException {
        JSONObject opts = new JSONObject()
            .put("amount", amount.longValue())       // amount in paise
            .put("currency", "INR")
            .put("receipt", receipt);

        Order order = razorpayClient.orders.create(opts);
        return new PlaceOrderResponse(
            order.get("id"),
            order.get("currency"),
            order.get("amount"),
            order.get("status")
        );
    }

    /**
     * Create by DTO (direct use) - identical to createPaymentSession.
     */
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

    /**
     * Verify Razorpay signature, fetch payment details, persist and respond.
     */
    public PaymentVerifyResponse verifyAndSave(PaymentVerifyRequest req) {
        Map<String,String> attrs = new HashMap<>();
        attrs.put("razorpay_order_id",   req.getRazorpayOrderId());
        attrs.put("razorpay_payment_id", req.getRazorpayPaymentId());
        attrs.put("razorpay_signature",  req.getRazorpaySignature());

        // signature validation
        try {
            Utils.verifyPaymentSignature(attrs, keySecret);
        } catch (RazorpayException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid signature");
        }

        try {
            var rzPay = razorpayClient.payments.fetch(req.getRazorpayPaymentId());
            Long amt     = rzPay.get("amount");
            String method= rzPay.get("method");
            String stat  = rzPay.get("status");
            Long ts      = rzPay.get("created_at");

            Payment p = new Payment();
            p.setOrderId(req.getRazorpayOrderId());
            p.setGateway("razorpay");
            p.setMethod(method);
            p.setStatus("captured".equalsIgnoreCase(stat)
                        ? PaymentStatus.PAID
                        : PaymentStatus.FAILED);
            p.setTransactionId(req.getRazorpayPaymentId());
            p.setAmount(amt);
            p.setPaidAt(OffsetDateTime.ofInstant(Instant.ofEpochSecond(ts), ZoneOffset.UTC));

            paymentRepository.save(p);
            return new PaymentVerifyResponse("Payment verified successfully", req.getRazorpayPaymentId());

        } catch (RazorpayException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to fetch payment details");
        }
    }

    /**
     * Retrieve stored payment details by Razorpay order ID.
     */
    public PaymentDetailDto fetchDetails(String orderId) {
        Payment p = paymentRepository.findByOrderId(orderId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No payment found for orderId: " + orderId));

        return new PaymentDetailDto(
            p.getPaymentId(),
            p.getOrderId(),
            p.getStatus().name(),
            p.getAmount(),
            p.getPaidAt()
        );
    }

    /**
     * Webhook handler: update a payment’s status based on payload.
     */
    public void processWebhook(Map<String,Object> payload) {
        String razorOrderId = (String) payload.get("razorpay_order_id");
        String event        = (String) payload.get("event");          // e.g. "payment.captured"
        Map<String,Object> data = (Map<String,Object>) payload.get("payload");
        Map<String,Object> paymentObj = (Map<String,Object>) data.get("payment");

        String paymentId = (String) ((Map<String,Object>)paymentObj.get("entity")).get("id");
        String status    = ((Map<String,Object>)paymentObj.get("entity")).get("status").toString();

        Payment p = paymentRepository.findByOrderId(razorOrderId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Payment not found for order: " + razorOrderId));

        p.setStatus("payment.captured".equals(event) ? PaymentStatus.PAID : p.getStatus());
        p.setUpdatedAt(OffsetDateTime.now());
        paymentRepository.save(p);
    }
}
