package com.vijaybrothers.store.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @Column(unique = true, nullable = false)
    private String orderNumber;                 // e.g. VB20240612-00015

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    private Instant createdAt;

    @Column
    private Instant updatedAt;

    // Shipping Information
    @Column(nullable = false)
    private String shippingName;

    @Column(nullable = false)
    private String shippingEmail;

    @Column(nullable = false)
    private String shippingPhone;

    @Column(nullable = false)
    private String shippingAddress;

    @Column(nullable = false)
    private String guestId;    // Added guest ID field

    // Relationships
    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
        if (orderStatus == null) {
            orderStatus = OrderStatus.PENDING;
        }
        if (paymentStatus == null) {
            paymentStatus = PaymentStatus.PENDING;
        }
        if (orderNumber == null) {
            orderNumber = generateOrderNumber();
        }
    }

    private String generateOrderNumber() {
        return String.format("VB%tY%<tm%<td-%X",
            Instant.now(),
            System.nanoTime() & 0xFFFF);
    }

    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
        item.setOrder(this);
    }

    public void removeOrderItem(OrderItem item) {
        orderItems.remove(item);
        item.setOrder(null);
    }
}
