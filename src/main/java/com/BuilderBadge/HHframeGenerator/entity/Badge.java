package com.BuilderBadge.HHframeGenerator.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "badges")
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String role;

    private String title;

    private String favoriteLanguage;

    @Column(length = 1000)
    private String imageUrl;

    private LocalDateTime createdAt;

    public Badge(String id, String name, String role,
                 String title, String favoriteLanguage,
                 String imageUrl, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.title = title;
        this.favoriteLanguage = favoriteLanguage;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }

    public Badge(){}

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}