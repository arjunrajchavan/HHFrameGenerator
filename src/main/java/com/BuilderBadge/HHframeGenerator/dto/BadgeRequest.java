package com.BuilderBadge.HHframeGenerator.dto;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BadgeRequest {

    private MultipartFile photo;

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must be 50 characters or less")
    private String name;

    @Size(max = 30, message = "Role must be 30 characters or less")
    private String role;

    @Size(max = 50, message = "Title must be 50 characters or less")
    private String title;

    @Size(max = 30, message = "Favorite language must be 30 characters or less")
    private String favoriteLanguage;

    public BadgeRequest(){}

    public BadgeRequest(MultipartFile photo, String name, String role, String title, String favoriteLanguage) {
        this.photo = photo;
        this.name = name;
        this.role = role;
        this.title = title;
        this.favoriteLanguage = favoriteLanguage;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
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
}