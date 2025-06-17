package com.vijaybrothers.store.service;

import com.vijaybrothers.store.dto.cart.CartView;
import com.vijaybrothers.store.dto.checkout.GuestCheckoutRequest;
import com.vijaybrothers.store.dto.checkout.GuestCheckoutResponse;
import com.vijaybrothers.store.dto.GuestInfoDto;
import com.vijaybrothers.store.model.Order;
import com.vijaybrothers.store.model.OrderItem;
import com.vijaybrothers.store.model.OrderStatus;
import com.vijaybrothers.store.model.PaymentStatus;
import com.vijaybrothers.store.model.Guest;
import com.vijaybrothers.store.repository.OrderItemRepository;
import com.vijaybrothers.store.repository.OrderRepository;
import com.vijaybrothers.store.repository.ProductRepository;
import com.vijaybrothers.store.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CartService cartService;
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final ProductRepository productRepo;
    private final PaymentService paymentService;
    private final GuestRepository guestRepo;

    @Transactional
    public GuestCheckoutResponse guestCheckout(GuestCheckoutRequest req) {
        // 1) Load cart snapshot
        CartView cart = cartService.buildView(req.cartId());
        if (cart.lines().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        // 2) Create Order
        String orderNumber = generateOrderNumber();
        Order order = Order.builder()            .orderNumber(orderNumber)
            .shippingName(req.name())
            .shippingEmail(req.email())
            .shippingPhone(req.phone())
            .shippingAddress(req.addressText())
            .totalAmount(cart.grandTotal())
            .paymentStatus(PaymentStatus.PENDING)
            .orderStatus(OrderStatus.PENDING)
            .createdAt(Instant.now())
            .build();
        order = orderRepo.save(order);

        // 3) Save each CartLine as an OrderItem
        for (var line : cart.lines()) {            OrderItem item = OrderItem.builder()
                .order(order)
                .product(productRepo.findById(line.productId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found")))
                .quantity(line.quantity())
                .unitPrice(line.price())
                .build();
            orderItemRepo.save(item);
        }

        // 4) Kick off payment & return session URL
        String sessionUrl = paymentService.createPaymentSession(orderNumber, cart.grandTotal());
        return new GuestCheckoutResponse(order.getOrderId(), orderNumber, sessionUrl);
    }

    private String generateOrderNumber() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uniq = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "VB" + date + "-" + uniq;
    }

    @Transactional(readOnly = true)
    public GuestInfoDto getGuestInfo(Integer guestId) {
        Guest guest = guestRepo.findById(guestId)
            .orElseThrow(() -> new IllegalArgumentException("Guest not found"));
        
        return new GuestInfoDto(
            guest.getGuestId(),
            guest.getName(),
            guest.getEmail(),
            guest.getPhone(),
            guest.getAddress(),
            guest.getCreatedAt(),
            guest.getUpdatedAt()
        );
    }
}
