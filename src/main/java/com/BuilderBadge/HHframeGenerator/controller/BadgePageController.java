package com.BuilderBadge.HHframeGenerator.controller;

import com.BuilderBadge.HHframeGenerator.dto.BadgeResponse;
import com.BuilderBadge.HHframeGenerator.service.BadgeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// No @RequestMapping("/api/badges") here — this must live at the site root
// so shared links look like yourapp.com/b/{id}, not /api/badges/b/{id}.
@Controller
public class BadgePageController {

    private final BadgeService badgeService;

    public BadgePageController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @GetMapping("/b/{id}")
    public String getBadgeSharePage(
            @PathVariable String id,
            Model model
    ) {
        BadgeResponse badge = badgeService.getBadge(id);
        model.addAttribute("badge", badge);
        return "badge"; // resolves to src/main/resources/templates/badge.html
    }
}