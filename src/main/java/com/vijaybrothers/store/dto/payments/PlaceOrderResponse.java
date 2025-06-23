package com.vijaybrothers.store.dto.payments;

public class PlaceOrderResponse {
    private String orderId;
    private String currency;
    private Long amount;
    private String status;

    public PlaceOrderResponse(String orderId, String currency, Long amount, String status) {
        this.orderId = orderId;
        this.currency = currency;
        this.amount = amount;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCurrency() {
        return currency;
    }

    public Long getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }
}
