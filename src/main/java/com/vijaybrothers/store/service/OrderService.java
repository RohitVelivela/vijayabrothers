package com.vijaybrothers.store.service;

import com.vijaybrothers.store.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    PlaceOrderResponse placeOrder(OrderCreateRequest request);
    Page<OrderSummaryDto> getGuestOrders(Long guestId, Pageable pageable);
    OrderTrackDto trackOrder(Long orderId);
}
