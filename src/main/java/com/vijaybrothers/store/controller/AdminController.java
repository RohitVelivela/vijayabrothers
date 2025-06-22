package com.vijaybrothers.store.controller;

import com.vijaybrothers.store.dto.signup.*;
import com.vijaybrothers.store.dto.login.*;
import com.vijaybrothers.store.dto.profile.*;
import com.vijaybrothers.store.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
@Validated
@Tag(name = "Admin", description = "Admin authentication and profile management endpoints")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @Operation(
        summary = "Login to admin account",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successful login"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
        }
    )
    @PostMapping("/login")
    public ResponseEntity<AdminLoginResponse> login(
            @Valid @RequestBody AdminLoginRequest request
    ) {
        AdminLoginResponse resp = adminService.login(request);
        return ResponseEntity.ok(resp);
    }

    @Operation(
        summary = "Update admin profile",
        security = { @SecurityRequirement(name = "bearer-jwt") },
        responses = {
            @ApiResponse(responseCode = "200", description = "Profile updated successfully"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
        }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/profile")
    public ResponseEntity<AdminProfileUpdateResponse> updateProfile(
            @Valid @RequestBody AdminProfileUpdateRequest request,
            @AuthenticationPrincipal UserDetails currentAdmin
    ) {
        AdminProfileUpdateResponse resp = adminService.updateProfile(currentAdmin.getUsername(), request);
        return ResponseEntity.ok(resp);
    }
}