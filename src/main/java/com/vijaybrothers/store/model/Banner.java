package com.vijaybrothers.store.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "banners")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banner_id")
    private Integer bannerId;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "link_to", nullable = false)
    private String linkTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}