package com.vijaybrothers.store.controller;

import com.vijaybrothers.store.dto.signup.AdminSignupRequest;
import com.vijaybrothers.store.dto.signup.AdminSignupResponse;
import com.vijaybrothers.store.dto.login.AdminLoginRequest;
import com.vijaybrothers.store.dto.login.AdminLoginResponse;
import com.vijaybrothers.store.service.impl.AdminServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin Signup", description = "Admin signup operations")
public class AdminSignupController {

    @Autowired
    private AdminServiceImpl adminService;

    @Operation(
        summary = "Create a new admin account",
        description = "Register a new admin user with username and password",
        responses = {
            @ApiResponse(responseCode = "201", description = "Admin account created successfully"),
            @ApiResponse(responseCode = "400", description = "Username already taken or invalid input")
        }
    )
    @PostMapping("/signup")
    public ResponseEntity<AdminSignupResponse> signup(@Valid @RequestBody AdminSignupRequest request) {
        AdminSignupResponse response = adminService.signup(request);
        if (response.getToken() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
