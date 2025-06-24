package com.vijaybrothers.store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDto {
    private String orderId;
    private Long amount;
    private String receipt; 
}

