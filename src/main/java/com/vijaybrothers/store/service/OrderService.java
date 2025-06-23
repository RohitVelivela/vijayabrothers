package com.vijaybrothers.store.service;

import com.vijaybrothers.store.dto.OrderCreateRequest;
import com.vijaybrothers.store.dto.PlaceOrderResponse;
import com.vijaybrothers.store.dto.OrderSummaryDto;
import com.vijaybrothers.store.dto.OrderTrackDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    PlaceOrderResponse placeOrder(OrderCreateRequest request);
    PlaceOrderResponse createOrder(OrderCreateRequest request);
    Page<OrderSummaryDto> getGuestOrders(String guestId, Pageable pageable);
    OrderTrackDto trackOrder(Integer orderId);
}
