package com.vijaybrothers.store.repository;

import com.vijaybrothers.store.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner,Integer> {
}