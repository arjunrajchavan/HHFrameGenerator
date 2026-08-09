package com.BuilderBadge.HHframeGenerator.controller;

import com.BuilderBadge.HHframeGenerator.dto.BadgeRequest;
import com.BuilderBadge.HHframeGenerator.dto.BadgeResponse;
import com.BuilderBadge.HHframeGenerator.service.BadgeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @ModelAttribute BadgeRequest request
    ) throws IOException {
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