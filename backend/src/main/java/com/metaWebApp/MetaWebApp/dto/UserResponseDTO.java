package com.metaWebApp.MetaWebApp.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for returning user information, excluding sensitive data like password hashes.
 */
public class UserResponseDTO {

    private UUID id;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isPremium;

    // Default constructor for Jackson
    public UserResponseDTO() {
    }

    // Constructor to easily convert from User entity
    public UserResponseDTO(UUID id, String email, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isPremium) {
        this.id = id;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isPremium = isPremium;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(Boolean premium) {
        isPremium = premium;
    }
}
