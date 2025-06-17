package com.vijaybrothers.store.service;

import com.vijaybrothers.store.dto.login.AdminLoginRequest;
import com.vijaybrothers.store.dto.login.AdminLoginResponse;
import com.vijaybrothers.store.dto.signup.AdminSignupRequest;
import com.vijaybrothers.store.dto.signup.AdminSignupResponse;
import com.vijaybrothers.store.dto.profile.AdminProfileUpdateRequest;
import com.vijaybrothers.store.dto.profile.AdminProfileUpdateResponse;

public interface AdminService {
    AdminSignupResponse signup(AdminSignupRequest req);
    AdminLoginResponse login(AdminLoginRequest req);
    AdminProfileUpdateResponse updateProfile(String currentUserEmail, AdminProfileUpdateRequest req);
}
