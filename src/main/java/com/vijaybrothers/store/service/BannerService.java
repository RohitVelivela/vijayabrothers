package com.vijaybrothers.store.service;

import com.vijaybrothers.store.dto.*;
import com.vijaybrothers.store.model.Banner;
import com.vijaybrothers.store.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository repo;

    /** List all banners (public) */
    @Transactional(readOnly = true)
    public List<BannerDto> getAllBanners() {
        return repo.findAll().stream()
            .map(b -> new BannerDto(
                b.getBannerId(),
                b.getImageUrl(),
                b.getLinkTo(),
                b.getCreatedAt()
            ))
            .toList();
    }

    /** Create a new banner (admin) */
    @Transactional
    public BannerDto createBanner(BannerCreateRequest req) {
        Banner b = new Banner();
        b.setImageUrl(req.imageUrl());
        b.setLinkTo(req.linkTo());
        b.setCreatedAt(Instant.now());
        Banner saved = repo.save(b);
        return new BannerDto(
            saved.getBannerId(),
            saved.getImageUrl(),
            saved.getLinkTo(),
            saved.getCreatedAt()
        );
    }

    /** Delete an existing banner (admin) */
    @Transactional
    public void deleteBanner(Integer id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Banner not found");
        }
        repo.deleteById(id);
    }
}