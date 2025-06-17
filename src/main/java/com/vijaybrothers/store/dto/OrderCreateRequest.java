package com.vijaybrothers.store.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class OrderCreateRequest {
    @NotBlank
    private String guestId;

    @Valid
    @NotEmpty
    private List<OrderItemDto> items;

    // Shipping Information
    @NotBlank
    private String shippingName;

    @NotBlank
    @Email
    private String shippingEmail;

    @NotBlank
    private String shippingPhone;

    @NotBlank
    private String shippingAddress;
}
