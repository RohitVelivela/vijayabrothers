package com.vijaybrothers.store.dto.payments;

import java.time.OffsetDateTime;

public class PaymentDetailDto {
    private Long paymentId;
    private String orderId;
    private String status;
    private Long amount;
    private OffsetDateTime paidAt;

    public PaymentDetailDto(Long paymentId, String orderId, String status, Long amount, OffsetDateTime paidAt) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.status = status;
        this.amount = amount;
        this.paidAt = paidAt;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public Long getAmount() {
        return amount;
    }

    public OffsetDateTime getPaidAt() {
        return paidAt;
    }
}
