package com.BuilderBadge.HHframeGenerator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaController {

    // Matches any path with no dot in it (so it won't hijack requests for
    // real static files like /assets/index-abc123.js or /favicon.ico).
    // /api/** and /b/{id} are more specific mappings, so Spring routes
    // those to BadgeController / BadgePageController first — this only
    // catches the leftovers, like /badge/abc123 or a plain refresh at /.
    @RequestMapping(value = "/{path:^(?!api|b).*$}")
    public String forwardToReactApp() {
        return "forward:/index.html";
    }
}