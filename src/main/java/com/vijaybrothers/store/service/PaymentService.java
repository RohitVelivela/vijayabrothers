package com.vijaybrothers.store.service;

import com.vijaybrothers.store.dto.*;
import com.vijaybrothers.store.model.Payment;
import com.vijaybrothers.store.model.PaymentStatus;
import com.vijaybrothers.store.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class PaymentService {

    private final RazorpayClient razorpayClient;
    private final PaymentRepository paymentRepository;
    private final String secret;

    public PaymentService(
            @Value("${razorpay.key_id}") String keyId,
            @Value("${razorpay.key_secret}") String secret,
            PaymentRepository paymentRepository
    ) throws RazorpayException {
        this.razorpayClient = new RazorpayClient(keyId, secret);
        this.secret = secret;
        this.paymentRepository = paymentRepository;
    }

    public PlaceOrderResponse createOrder(PaymentCreateRequest req) throws RazorpayException {
        JSONObject opts = new JSONObject()
            .put("amount", req.getAmount().longValue())
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

    public PaymentDetailDto fetchDetails(String orderId) {
    Payment p = paymentRepository.findByOrderId(orderId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No payment found for order: " + orderId));
    return new PaymentDetailDto(
        p.getPaymentId(),
        p.getOrderId(),
        p.getStatus().name(),
        p.getAmount(),
        p.getPaidAt()
    );
}

    public PaymentVerifyResponse verifyAndSave(PaymentVerifyRequest req) {
        JSONObject sig = new JSONObject()
            .put("razorpay_order_id", req.getRazorpayOrderId())
            .put("razorpay_payment_id", req.getRazorpayPaymentId())
            .put("razorpay_signature", req.getRazorpaySignature());
        try {
            Utils.verifyPaymentSignature(sig, secret);
        } catch (RazorpayException e) {
            throw new ResponseStatusException(BAD_REQUEST, "Invalid signature");
        }

        try {
            com.razorpay.Payment rzPay = razorpayClient.payments.fetch(req.getRazorpayPaymentId());
            Long amount = rzPay.get("amount");
            String method = rzPay.get("method");
            String status = rzPay.get("status");
            Long ts = rzPay.get("created_at");

            Payment p = new Payment();
            p.setOrderId(req.getRazorpayOrderId());
            p.setGateway("razorpay");
            p.setMethod(method);
            p.setStatus("captured".equalsIgnoreCase(status) ? PaymentStatus.PAID : PaymentStatus.FAILED);
            p.setTransactionId(req.getRazorpayPaymentId());
            p.setAmount(amount);
            p.setPaidAt(OffsetDateTime.ofInstant(Instant.ofEpochSecond(ts), ZoneOffset.UTC));
            paymentRepository.save(p);

            return new PaymentVerifyResponse("Payment verified successfully", req.getRazorpayPaymentId());
        } catch (RazorpayException ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Failed to fetch payment details");
        }
    }

    public PlaceOrderResponse createPaymentSession(String orderId, BigDecimal amount) {
        return new PlaceOrderResponse(orderId, "INR", amount.longValue(), "created");
    }

    public String createPaymentOrder(PaymentRequestDto req) {
        try {
            JSONObject opts = new JSONObject()
                .put("amount", req.getAmount().longValue())
                .put("currency", "INR")
                .put("receipt", req.getReceipt());
            Order order = razorpayClient.orders.create(opts);
            return order.get("id");
        } catch (RazorpayException e) {
            throw new ResponseStatusException(BAD_REQUEST, "Failed to create payment order");
        }
    }

    public String verifyAndSavePayment(PaymentVerificationDto req) {
        JSONObject sig = new JSONObject()
            .put("razorpay_order_id", req.getRazorpayOrderId())
            .put("razorpay_payment_id", req.getRazorpayPaymentId())
            .put("razorpay_signature", req.getRazorpaySignature());
        try {
            Utils.verifyPaymentSignature(sig, secret);
        } catch (RazorpayException e) {
            throw new ResponseStatusException(BAD_REQUEST, "Invalid signature");
        }

        try {
            com.razorpay.Payment rzPay = razorpayClient.payments.fetch(req.getRazorpayPaymentId());
            Long amount = rzPay.get("amount");
            String method = rzPay.get("method");
            String status = rzPay.get("status");
            Long ts = rzPay.get("created_at");

            Payment p = new Payment();
            p.setOrderId(req.getRazorpayOrderId());
            p.setGateway("razorpay");
            p.setMethod(method);
            p.setStatus("captured".equalsIgnoreCase(status) ? PaymentStatus.PAID : PaymentStatus.FAILED);
            p.setTransactionId(req.getRazorpayPaymentId());
            p.setAmount(amount);
            p.setPaidAt(OffsetDateTime.ofInstant(Instant.ofEpochSecond(ts), ZoneOffset.UTC));
            paymentRepository.save(p);

            return req.getRazorpayPaymentId();
        } catch (RazorpayException ex) {
            throw new ResponseStatusException(BAD_REQUEST, "Failed to fetch payment details");
        }
    }

    public void handleWebhook(String payload) {
        System.out.println("Webhook received:\n" + payload);
    }
}
