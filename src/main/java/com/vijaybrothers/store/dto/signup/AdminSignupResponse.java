package com.vijaybrothers.store.dto.signup;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdminSignupResponse {
    @JsonProperty("token")
    private String token;

    public AdminSignupResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
