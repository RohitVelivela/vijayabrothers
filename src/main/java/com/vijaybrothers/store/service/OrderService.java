package com.vijaybrothers.store.service;

import com.vijaybrothers.store.dto.*;
import com.vijaybrothers.store.model.*;
import com.vijaybrothers.store.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;
    private final PaymentService paymentService;
    private static final AtomicInteger ORDER_SEQUENCE = new AtomicInteger(1);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Transactional
    public PlaceOrderResponse createOrder(OrderCreateRequest req) {
        // Generate unique order number: VB20240612-00001
        String dateStr = LocalDate.now().format(DATE_FORMAT);
        String orderNumber = String.format("VB%s-%05d", dateStr, ORDER_SEQUENCE.getAndIncrement());
        
        // Calculate total amount and validate products
        BigDecimal totalAmount = BigDecimal.ZERO;
        for(OrderItemDto item : req.getItems()) {
            Product product = productRepo.findById(item.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + item.productId()));
                
            totalAmount = totalAmount.add(product.getPrice().multiply(new BigDecimal(item.quantity())));
        }

        // Create and save initial order
        final Order newOrder = orderRepo.save(Order.builder()
            .orderNumber(orderNumber)
            .totalAmount(totalAmount)
            .orderStatus(OrderStatus.PENDING)
            .paymentStatus(PaymentStatus.PENDING)
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .shippingName(req.getShippingName())
            .shippingEmail(req.getShippingEmail())
            .shippingPhone(req.getShippingPhone())
            .shippingAddress(req.getShippingAddress())
            .guestId(req.getGuestId())
            .build());

        // Create order items
        List<OrderItem> orderItems = req.getItems().stream()
            .map(item -> {
                Product product = productRepo.getReferenceById(item.productId());
                return OrderItem.builder()
                    .order(newOrder)
                    .product(product)
                    .quantity(item.quantity())
                    .unitPrice(product.getPrice())
                    .build();
            })
            .collect(java.util.stream.Collectors.toList());

        // Update order with items and save again
        newOrder.setOrderItems(orderItems);
        orderRepo.save(newOrder);

        // Create payment session
        String paymentUrl = paymentService.createPaymentSession(newOrder.getOrderNumber(), newOrder.getTotalAmount());

        return PlaceOrderResponse.builder()
            .orderNumber(orderNumber)
            .paymentUrl(paymentUrl)
            .build();
    }    public Page<OrderSummaryDto> getGuestOrders(String guestId, Pageable pageable) {
        return orderRepo.findByGuestIdOrderByCreatedAtDesc(guestId, pageable)
            .map(order -> OrderSummaryDto.builder()
                .orderNumber(order.getOrderNumber())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus().name())
                .paymentStatus(order.getPaymentStatus().name())
                .createdAt(order.getCreatedAt())
                .build());
    }

    @Transactional(readOnly = true)
    public OrderTrackDto trackOrder(Integer orderId) {
        Order order = orderRepo.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));
            
        return new OrderTrackDto(
            order.getOrderNumber(),
            order.getOrderStatus().name(),
            order.getPaymentStatus().name(),
            order.getUpdatedAt()
        );
    }
}
