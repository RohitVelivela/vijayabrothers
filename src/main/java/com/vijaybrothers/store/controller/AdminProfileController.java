package com.vijaybrothers.store.controller;

import com.vijaybrothers.store.dto.profile.AdminProfileUpdateRequest;
import com.vijaybrothers.store.dto.profile.AdminProfileUpdateResponse;
import com.vijaybrothers.store.service.impl.AdminServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
