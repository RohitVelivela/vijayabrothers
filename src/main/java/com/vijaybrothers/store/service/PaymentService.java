package com.vijaybrothers.store.service;

import com.vijaybrothers.store.dto.PaymentCreateRequest;
import com.vijaybrothers.store.dto.PaymentDetailDto;


import com.vijaybrothers.store.model.Order;
import com.vijaybrothers.store.model.OrderStatus;
import com.vijaybrothers.store.model.Payment;
import com.vijaybrothers.store.model.PaymentStatus;
import com.vijaybrothers.store.repository.OrderRepository;
import com.vijaybrothers.store.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepo;
    private final PaymentRepository paymentRepo;/**
     * In a real integration you'd call Razorpay/PayU/Stripe here.
     */
    public String createPaymentSession(String orderNumber, BigDecimal amount) {
        // Stub: redirect customer to a payment page
        return "https://payments.example.com/session/" + orderNumber;
    }

    /**
     * Update order/payment status based on gateway payload.
     */
    @Transactional
    public void processWebhook(Map<String, Object> payload) {
        String orderNumber = (String) payload.get("orderNumber");
        String status = ((String) payload.get("status")).toUpperCase();

        Order order = orderRepo.findByOrderNumber(orderNumber)
            .orElseThrow(() -> new IllegalArgumentException("Unknown order: " + orderNumber));

        // Map gateway status to your enums
        if ("COMPLETED".equals(status) || "PAID".equals(status)) {
            order.setPaymentStatus(PaymentStatus.PAID);
            order.setOrderStatus(OrderStatus.CONFIRMED);
        } else if ("FAILED".equals(status) || "CANCELLED".equals(status)) {
            order.setPaymentStatus(PaymentStatus.FAILED);
            order.setOrderStatus(OrderStatus.CANCELLED);
        } else {
            // leave as-is or log unknown
        }

        orderRepo.save(order);
    }

    @Transactional
    public void recordPayment(PaymentCreateRequest req) {
        // Check for duplicate transaction
        if (paymentRepo.findByTransactionId(req.transactionId()).isPresent()) {
            throw new IllegalArgumentException("Duplicate transaction ID");
        }

        // Find and validate order
        Order order = orderRepo.findById(req.orderId())
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // Validate amount matches order
        if (!order.getTotalAmount().equals(req.amount())) {
            throw new IllegalArgumentException("Payment amount doesn't match order total");
        }

        // Create and save payment
        Payment payment = Payment.builder()
            .order(order)
            .amount(req.amount())
            .method(req.method())
            .transactionId(req.transactionId())
            .status(PaymentStatus.valueOf(req.status().toUpperCase()))
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();

        paymentRepo.save(payment);

        // Update order payment status
        order.setPaymentStatus(PaymentStatus.valueOf(req.status().toUpperCase()));
        order.setUpdatedAt(Instant.now());
        orderRepo.save(order);
    }

    @Transactional(readOnly = true)
    public PaymentDetailDto getPaymentByOrder(Integer orderId) {
        Payment payment = paymentRepo.findByOrderOrderId(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Payment not found"));
        return new PaymentDetailDto(
            payment.getPaymentId().longValue(),
            payment.getOrder().getOrderId().longValue(),
            payment.getAmount(),
            payment.getMethod(),
            payment.getTransactionId(),
            payment.getStatus().name(),
            payment.getCreatedAt()
        );
    }
}
