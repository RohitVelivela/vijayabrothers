package com.vijaybrothers.store.dto.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminLoginResponse {
    @JsonProperty("token")
    private String token;

    @JsonProperty("user_image")
    private String userImage;

    public AdminLoginResponse(String token, String userImage) {
        this.token = token;
        this.userImage = userImage;
    }

    public String getToken() {
        return token;
    }

    public String getUserImage() {
        return userImage;
    }
}
