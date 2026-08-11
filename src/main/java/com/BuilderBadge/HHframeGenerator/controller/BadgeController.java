package com.BuilderBadge.HHframeGenerator.controller;

import com.BuilderBadge.HHframeGenerator.dto.BadgeRequest;
import com.BuilderBadge.HHframeGenerator.dto.BadgeResponse;
import com.BuilderBadge.HHframeGenerator.service.BadgeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/badges")
public class BadgeController {

    private final BadgeService badgeService;

    public BadgeController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @PostMapping
    public ResponseEntity<BadgeResponse> createBadge(
            @Valid @ModelAttribute BadgeRequest request
    ) throws IOException {
        MultipartFile photo = request.getPhoto();
        if (photo == null || photo.isEmpty()) {
            throw new IllegalArgumentException("Photo is required");
        }

        String contentType = photo.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Uploaded file must be a valid image");
        }

        BadgeResponse response = badgeService.createBadge(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BadgeResponse> getBadge(
            @PathVariable String id
    ) {
        BadgeResponse response = badgeService.getBadge(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<Void> getBadgeImage(
            @PathVariable String id
    ) {
        String imageUrl = badgeService.getBadgeImageUrl(id);
        return ResponseEntity
                .status(302)
                .location(java.net.URI.create(imageUrl))
                .build();
    }
}