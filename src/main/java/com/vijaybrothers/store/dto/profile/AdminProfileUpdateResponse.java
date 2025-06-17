package com.vijaybrothers.store.dto.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminProfileUpdateResponse {
    @JsonProperty("token")
    private String token;

    @JsonProperty("user_image")
    private String userImage;

    public AdminProfileUpdateResponse(String token, String userImage) {
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
