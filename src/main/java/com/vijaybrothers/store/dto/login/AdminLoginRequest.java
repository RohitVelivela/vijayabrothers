package com.vijaybrothers.store.dto.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public class AdminLoginRequest {
    @JsonProperty("user_email")
    @NotBlank @Email
    private String userEmail;

    @JsonProperty("user_password")
    @NotBlank
    private String userPassword;

    // getters & setters
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getUserPassword() { return userPassword; }
    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }
}
