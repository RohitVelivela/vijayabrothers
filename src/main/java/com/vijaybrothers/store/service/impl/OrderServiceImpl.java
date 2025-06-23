package com.vijaybrothers.store.service.impl;

import com.vijaybrothers.store.dto.OrderCreateRequest;
import com.vijaybrothers.store.dto.OrderItemRequest;
import com.vijaybrothers.store.dto.PlaceOrderResponse;
import com.vijaybrothers.store.dto.OrderSummaryDto;
import com.vijaybrothers.store.dto.OrderTrackDto;
import com.vijaybrothers.store.model.Order;
import com.vijaybrothers.store.model.OrderItem;
import com.vijaybrothers.store.model.OrderStatus;
import com.vijaybrothers.store.repository.OrderItemRepository;
import com.vijaybrothers.store.repository.OrderRepository;
import com.vijaybrothers.store.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public PlaceOrderResponse placeOrder(OrderCreateRequest req) {
        // Generate unique order number
        String orderNumber = "ORD" +
            ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));

        // Sum up subTotals
        BigDecimal totalAmount = req.items().stream()
            .map(OrderItemRequest::subTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create and save Order
        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setGuestId(req.guestId());
        order.setTotalAmount(totalAmount);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setShippingName(req.shippingName());
        order.setShippingEmail(req.shippingEmail());
        order.setShippingPhone(req.shippingPhone());
        order.setShippingAddress(req.shippingAddress());
        order.setShippingCity(req.shippingCity());
        order.setShippingPostalCode(req.shippingPostalCode());
        order.setShippingState(req.shippingState());
        order.setCreatedAt(ZonedDateTime.now());
        order = orderRepository.save(order);

        // Create and save OrderItems
        for (OrderItemRequest itemReq : req.items()) {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductId(itemReq.productId());
            item.setQuantity(itemReq.quantity());
            item.setUnitPrice(itemReq.unitPrice());
            item.setLineTotal(itemReq.subTotal());
            orderItemRepository.save(item);
        }

        // Return response
        return new PlaceOrderResponse(
            order.getOrderId(),
            order.getOrderNumber(),
            order.getTotalAmount(),
            order.getOrderStatus().name()
        );
    }

    @Override
    public PlaceOrderResponse createOrder(OrderCreateRequest request) {
        return placeOrder(request);
    }

    @Override
    public Page<OrderSummaryDto> getGuestOrders(String guestId, Pageable pageable) {
        Integer guestIdInt = Integer.valueOf(guestId);
        return orderRepository.findByGuestId(guestIdInt, pageable)
            .map(order -> OrderSummaryDto.builder()
                .orderNumber(order.getOrderNumber())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus().name())
                .paymentStatus(order.getPaymentStatus().name())
                .createdAt(order.getCreatedAt().toInstant())
                .build());
    }

    @Override
    public OrderTrackDto trackOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId.longValue())
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        
        return new OrderTrackDto(
            order.getOrderNumber(),
            order.getOrderStatus().name(),
            order.getPaymentStatus().name(),
            order.getUpdatedAt()
        );
    }
}
