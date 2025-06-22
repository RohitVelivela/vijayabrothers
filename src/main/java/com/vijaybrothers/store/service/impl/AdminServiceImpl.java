package com.vijaybrothers.store.service.impl;

import com.vijaybrothers.store.dto.signup.AdminSignupRequest;
import com.vijaybrothers.store.dto.signup.AdminSignupResponse;
import com.vijaybrothers.store.dto.login.AdminLoginRequest;
import com.vijaybrothers.store.dto.login.AdminLoginResponse;
import com.vijaybrothers.store.dto.profile.AdminProfileUpdateRequest;
import com.vijaybrothers.store.dto.profile.AdminProfileUpdateResponse;
import com.vijaybrothers.store.model.UserDetail;
import com.vijaybrothers.store.repository.UserDetailRepository;
import com.vijaybrothers.store.security.JwtUtil;
import com.vijaybrothers.store.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Base64;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public AdminSignupResponse signup(AdminSignupRequest request) {
        // Check if username exists
        Optional<UserDetail> existingUser = userDetailRepository.findByUserName(request.getUserName());
        if (existingUser.isPresent()) {
            return new AdminSignupResponse(null, "Username already taken");
        }

        // Create new user
        UserDetail user = new UserDetail();
        user.setUserName(request.getUserName());
        user.setPasswordHash(passwordEncoder.encode(request.getUserPassword()));
        user.setUserEmail(request.getUserEmail());
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());

        userDetailRepository.save(user);

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getUserName());
        return new AdminSignupResponse(token, "Admin account created successfully");
    }

    @Override
    public AdminLoginResponse login(AdminLoginRequest request) {
        Optional<UserDetail> user = userDetailRepository.findByUserName(request.getUserName());
        if (user.isPresent() && passwordEncoder.matches(request.getUserPassword(), user.get().getPasswordHash())) {
            // Generate JWT token
            String token = jwtUtil.generateToken(user.get().getUserName());
            String userImage = user.get().getUserImage() != null ? 
                             Base64.getEncoder().encodeToString(user.get().getUserImage()) : null;
            return new AdminLoginResponse(token, userImage);
        }
        return new AdminLoginResponse(null, null);
    }

    @Override
    public AdminProfileUpdateResponse updateProfile(String currentUserName, AdminProfileUpdateRequest request) {
        Optional<UserDetail> user = userDetailRepository.findByUserName(currentUserName);
             
        if (user.isPresent()) {
            UserDetail userDetail = user.get();
            if (request.getUserEmail() != null) {
                userDetail.setUserEmail(request.getUserEmail());
            }
            if (request.isPasswordChanged() && request.getNewPassword() != null) {
                userDetail.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
            }
            if (request.getUserImage() != null) {
                userDetail.setUserImage(Base64.getDecoder().decode(request.getUserImage()));
            }
            userDetail.setUpdatedAt(Instant.now());
            userDetailRepository.save(userDetail);
            
            // Generate new JWT token
            String token = jwtUtil.generateToken(userDetail.getUserName());
            String userImage = userDetail.getUserImage() != null ? 
                             Base64.getEncoder().encodeToString(userDetail.getUserImage()) : null;
            return new AdminProfileUpdateResponse(token, userImage, "Profile updated successfully");
        }
        return new AdminProfileUpdateResponse(null, null, "Profile update failed");
    }
}
