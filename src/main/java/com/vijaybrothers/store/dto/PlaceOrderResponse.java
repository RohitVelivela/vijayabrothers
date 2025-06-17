package com.vijaybrothers.store.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaceOrderResponse {
    private String orderNumber;
    private String paymentUrl;
}
