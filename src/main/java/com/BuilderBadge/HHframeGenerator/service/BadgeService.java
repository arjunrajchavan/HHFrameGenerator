//package com.BuilderBadge.HHframeGenerator.service;
//
//import com.BuilderBadge.HHframeGenerator.controller.BadgeController;
//import com.BuilderBadge.HHframeGenerator.dto.BadgeRequest;
//import com.BuilderBadge.HHframeGenerator.dto.BadgeResponse;
//import com.BuilderBadge.HHframeGenerator.entity.Badge;
//import com.BuilderBadge.HHframeGenerator.repository.BadgeRepository;
//import org.springframework.stereotype.Service;
//import java.time.LocalDateTime;
//
//@Service
//public class BadgeService {
//    public BadgeService(BadgeRepository repository){
//        this.badgeRepository = repository;
//    }
//    private BadgeRepository badgeRepository;
//
//    public BadgeResponse createBadge(BadgeRequest request){
//        Badge badge = new Badge();
//
//        badge.setName(request.getName());
//        badge.setRole(request.getRole());
//        badge.setTitle(request.getTitle());
//        badge.setFavoriteLanguage(request.getFavoriteLanguage());
//        badge.setCreatedAt(LocalDateTime.now());
//
//        Badge savedBadge = badgeRepository.save(badge);
//
//        return new BadgeResponse(
//                savedBadge.getId(),
//                savedBadge.getName(),
//                savedBadge.getRole(),
//                savedBadge.getTitle(),
//                savedBadge.getFavoriteLanguage(),
//                savedBadge.getImageUrl(),
//                "/b/" + savedBadge.getId()
//        );
//
//    }
//
//    public BadgeResponse getBadge(String id) {
//
//        Badge badge = badgeRepository.findById(id)
//                .orElseThrow(() ->
//                        new RuntimeException("Badge not found with id: " + id)
//                );
//
//        return new BadgeResponse(
//                badge.getId(),
//                badge.getName(),
//                badge.getRole(),
//                badge.getTitle(),
//                badge.getFavoriteLanguage(),
//                badge.getImageUrl(),
//                "/b/" + badge.getId()
//        );
//    }
//
//    public String getBadgeImageUrl(String id) {
//
//        Badge badge = badgeRepository.findById(id)
//                .orElseThrow(() ->
//                        new RuntimeException("Badge not found with id: " + id)
//                );
//
//        return badge.getImageUrl();
//    }
//
//
//}

package com.BuilderBadge.HHframeGenerator.service;

import com.BuilderBadge.HHframeGenerator.dto.BadgeRequest;
import com.BuilderBadge.HHframeGenerator.dto.BadgeResponse;
import com.BuilderBadge.HHframeGenerator.entity.Badge;
import com.BuilderBadge.HHframeGenerator.exceptions.BadgeNotFoundException;
import com.BuilderBadge.HHframeGenerator.repository.BadgeRepository;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final BadgeRenderer badgeRenderer;
    private final ImageStorageService imageStorageService;

    public BadgeService(BadgeRepository badgeRepository,
                        BadgeRenderer badgeRenderer,
                        ImageStorageService imageStorageService) {
        this.badgeRepository = badgeRepository;
        this.badgeRenderer = badgeRenderer;
        this.imageStorageService = imageStorageService;
    }

    public BadgeResponse createBadge(BadgeRequest request) throws IOException {
        // 1. Render the badge image server-side (photo + name + role + title)
        BufferedImage badgeImage = badgeRenderer.render(request);

        // 2. Upload the rendered PNG to Cloudinary/S3, get back a public URL
        String imageUrl = imageStorageService.upload(badgeImage);

        // 3. Persist the badge with the real image URL
        Badge badge = new Badge();
        badge.setName(request.getName());
        badge.setRole(request.getRole());
        badge.setTitle(request.getTitle());
        badge.setFavoriteLanguage(request.getFavoriteLanguage());
        badge.setImageUrl(imageUrl);
        badge.setCreatedAt(LocalDateTime.now());

        Badge savedBadge = badgeRepository.save(badge);

        return toResponse(savedBadge);
    }

    public BadgeResponse getBadge(String id) {
        Badge badge = badgeRepository.findById(id)
                .orElseThrow(() -> new BadgeNotFoundException(id));

        return toResponse(badge);
    }

    public String getBadgeImageUrl(String id) {
        Badge badge = badgeRepository.findById(id)
                .orElseThrow(() -> new BadgeNotFoundException(id));

        return badge.getImageUrl();
    }

    private BadgeResponse toResponse(Badge badge) {
        return new BadgeResponse(
                badge.getId(),
                badge.getName(),
                badge.getRole(),
                badge.getTitle(),
                badge.getFavoriteLanguage(),
                badge.getImageUrl(),
                "/b/" + badge.getId()
        );
    }
}