package com.vijaybrothers.store.controller;

import com.vijaybrothers.store.dto.BannerDto;
import com.vijaybrothers.store.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banners")
@RequiredArgsConstructor
public class BannerController {

    private final BannerService svc;

    /**
     * GET /api/banners
     * — Public: fetch all homepage banners
     */
    @GetMapping
    public List<BannerDto> getAllBanners() {
        return svc.getAllBanners();
    }
}