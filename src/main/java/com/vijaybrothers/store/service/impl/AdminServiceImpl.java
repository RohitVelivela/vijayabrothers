package com.vijaybrothers.store.service.impl;

import com.vijaybrothers.store.dto.login.AdminLoginRequest;
import com.vijaybrothers.store.dto.login.AdminLoginResponse;
import com.vijaybrothers.store.dto.signup.AdminSignupRequest;
import com.vijaybrothers.store.dto.signup.AdminSignupResponse;
import com.vijaybrothers.store.dto.profile.AdminProfileUpdateRequest;
import com.vijaybrothers.store.dto.profile.AdminProfileUpdateResponse;
import com.vijaybrothers.store.model.Admin;
import com.vijaybrothers.store.repository.AdminRepository;
import com.vijaybrothers.store.security.JwtUtil;
import com.vijaybrothers.store.service.AdminService;

import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.time.Instant;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepo;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AdminServiceImpl(AdminRepository adminRepo,
                            AuthenticationManager authManager,
                            PasswordEncoder passwordEncoder,
                            JwtUtil jwtUtil) {
        this.adminRepo = adminRepo;
        this.authManager = authManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AdminSignupResponse signup(AdminSignupRequest req) {
        if (adminRepo.existsByUserEmail(req.getUserEmail()))
            throw new RuntimeException("Email already in use");
        if (adminRepo.existsByUserName(req.getUserName()))
            throw new RuntimeException("Username already taken");

        Admin admin = new Admin();
        admin.setUserName(req.getUserName());
        admin.setUserEmail(req.getUserEmail());
        admin.setPassword(passwordEncoder.encode(req.getUserPassword()));
        admin = adminRepo.save(admin);

        String token = jwtUtil.generateToken(admin.getUserEmail());
        return new AdminSignupResponse(token); // Only token for signup
    }

    @Override
    public AdminLoginResponse login(AdminLoginRequest req) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getUserEmail(), req.getUserPassword())
        );
        
        Admin admin = adminRepo.findByUserEmail(req.getUserEmail())
            .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));
            
        String token = jwtUtil.generateToken(admin.getUserEmail());
        return new AdminLoginResponse(token, admin.getUserImage()); // Token and user_image for login
    }

    @Override
    public AdminProfileUpdateResponse updateProfile(String currentUserEmail, AdminProfileUpdateRequest req) {
        Admin admin = adminRepo.findByUserEmail(currentUserEmail)
            .orElseThrow(() -> new EntityNotFoundException("Admin not found"));

        if (!currentUserEmail.equals(req.getUserEmail()) && adminRepo.existsByUserEmail(req.getUserEmail())) {
            throw new RuntimeException("Email already in use");
        }

        admin.setUserName(req.getUserName());
        admin.setUserEmail(req.getUserEmail());
        
        if (req.isPasswordChanged()) {
            if (!passwordEncoder.matches(req.getOldPassword(), admin.getPassword())) {
                throw new RuntimeException("Old password incorrect");
            }
            admin.setPassword(passwordEncoder.encode(req.getNewPassword()));
        }
        
        if (req.getUserImage() != null) {
            admin.setUserImage(req.getUserImage());
        }
        admin.setUpdatedAt(Instant.now());        admin = adminRepo.save(admin);
        
        String token = jwtUtil.generateToken(admin.getUserEmail());
        return new AdminProfileUpdateResponse(token, admin.getUserImage()); // Return token and user_image
    }
}
