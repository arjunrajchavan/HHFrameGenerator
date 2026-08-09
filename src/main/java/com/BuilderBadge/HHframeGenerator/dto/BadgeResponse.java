package com.BuilderBadge.HHframeGenerator.dto;

public class BadgeResponse {

    private String id;

    private String name;

    private String role;

    private String title;

    private String favoriteLanguage;

    private String imageUrl;

    private String shareUrl;

    public BadgeResponse(String id, String name, String role,
                         String title, String favoriteLanguage, String imageUrl, String shareUrl) {

        this.id = id;
        this.name = name;
        this.role = role;
        this.title = title;
        this.favoriteLanguage = favoriteLanguage;
        this.imageUrl = imageUrl;
        this.shareUrl = shareUrl;
    }
    public BadgeResponse(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFavoriteLanguage() {
        return favoriteLanguage;
    }

    public void setFavoriteLanguage(String favoriteLanguage) {
        this.favoriteLanguage = favoriteLanguage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }
}