package com.vijaybrothers.store.dto.checkout;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record GuestCheckoutRequest(
    @NotBlank String cartId,
    @NotBlank String name,
    @Email String email,
    @NotBlank String phone,
    @NotBlank String addressText,
    @NotBlank String city,
    @NotBlank String state,
    @NotBlank String postalCode
) {}
