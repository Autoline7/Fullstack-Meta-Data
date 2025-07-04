package com.metaWebApp.MetaWebApp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a password reset request in the database.
 * This entity maps to the 'password_resets' table.
 */
@Entity
@Table(name = "password_resets")
public class PasswordReset {

    /**
     * Unique identifier for each password reset request.
     * Generated automatically as a UUID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    /**
     * The user for whom this password reset was requested.
     * This is a Many-to-One relationship, as one user can have many password reset requests.
     * The 'user_id' column in the database will store the UUID of the associated User.
     */
    @ManyToOne(fetch = FetchType.LAZY) // Lazy fetching for performance
    @JoinColumn(name = "user_id", nullable = false) // Foreign key column
    private User user; // The associated User entity

    /**
     * The unique, time-limited token sent to the user (e.g., via email).
     * Must be unique to prevent collisions and ensure each request has a distinct token.
     */
    @Column(name = "token", nullable = false, unique = true, length = 255)
    private String token;

    /**
     * The date and time when this token becomes invalid.
     */
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    /**
     * A flag indicating if this token has already been used for a password reset.
     * Defaults to false, set to true upon successful use.
     */
    @Column(name = "used", nullable = false)
    private Boolean used = false; // Initialize to false

    /**
     * The timestamp when this password reset request was initiated.
     * Automatically set on creation.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    // --- Constructors ---
    public PasswordReset() {
        // Default constructor required by JPA
    }

    // Convenient constructor for creating a new password reset request
    public PasswordReset(User user, String token, LocalDateTime expiresAt) {
        this.user = user;
        this.token = token;
        this.expiresAt = expiresAt;
        this.used = false; // Default
        // createdAt will be set by @PrePersist
    }

    // --- Lifecycle Callbacks ---
    // Automatically set createdAt timestamp and default 'used' status on entity creation
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (used == null) { // Ensure default if not set by constructor
            used = false;
        }
    }


    // --- Getters and Setters ---
    // (Generate these or write manually)

    public UUID getId() {
        return id;
    }

    // No public setter for auto-generated ID

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // No public setter for createdAt

    @Override
    public String toString() {
        return "PasswordReset{" +
                "id=" + id +
                ", userId=" + (user != null ? user.getId() : "null") +
                ", token='" + token + '\'' +
                ", expiresAt=" + expiresAt +
                ", used=" + used +
                ", createdAt=" + createdAt +
                '}';
    }
}
