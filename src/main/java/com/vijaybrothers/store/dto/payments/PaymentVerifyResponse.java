package com.vijaybrothers.store.dto.payments;

public class PaymentVerifyResponse {
    private String message;
    private String paymentId;

    public PaymentVerifyResponse(String message, String paymentId) {
        this.message = message;
        this.paymentId = paymentId;
    }

    public String getMessage() {
        return message;
    }

    public String getPaymentId() {
        return paymentId;
    }
}
