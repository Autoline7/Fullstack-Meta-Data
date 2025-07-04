package com.metaWebApp.MetaWebApp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a user account in the database.
 * This entity maps to the 'users' table.
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * Unique identifier for each user.
     * Generated automatically as a UUID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Generates a UUID for the primary key
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    /**
     * User's email address, must be unique across all users.
     * Used for login and communication.
     */
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    /**
     * The securely hashed and salted password for security.
     * Never store plain text passwords.
     */
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    /**
     * Timestamp when the user's account was first created.
     * Automatically set on creation.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Last time any information in this user's record was modified.
     * Automatically updated on any entity modification.
     */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Flag to quickly indicate if a user currently has premium access.
     * Default to false.
     */
    @Column(name = "is_premium", nullable = false)
    private Boolean isPremium = false; // Initialize to false

    // --- Relationships ---

    /**
     * A user can have many data uploads.
     * 'mappedBy' indicates that the 'user' field in UserDataUpload is the owner of this relationship.
     * Using 'CascadeType.ALL' means operations (like delete) on User will cascade to its UserDataUploads.
     * 'orphanRemoval = true' ensures that if a UserDataUpload is disassociated from a User, it's deleted.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<UserDataUpload> uploads = new HashSet<>();

    /**
     * A user can have at most one subscription.
     * 'mappedBy' indicates that the 'user' field in Subscription is the owner of this relationship.
     * 'CascadeType.ALL' means operations (like delete) on User will cascade to its Subscription.
     * 'orphanRemoval = true' ensures that if a Subscription is disassociated from a User, it's deleted.
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Subscription subscription;

    /**
     * A user can request many password resets over time.
     * 'mappedBy' indicates that the 'user' field in PasswordReset is the owner of this relationship.
     * 'CascadeType.ALL' and 'orphanRemoval = true' for managing associated reset tokens.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<PasswordReset> passwordResets = new HashSet<>();


    // --- Constructors ---
    public User() {
        // Default constructor required by JPA
    }

    // Convenient constructor for creating new users
    public User(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.isPremium = false; // Default to false
    }

    // --- Lifecycle Callbacks ---
    // Automatically set createdAt and updatedAt timestamps on entity creation
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    // Automatically update updatedAt timestamp on entity modification
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // --- Getters and Setters ---
    // (Generate these or write manually)

    public UUID getId() {
        return id;
    }

    // Id setter is usually not public when auto-generated, but can be useful for testing
    // public void setId(UUID id) { this.id = id; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // No public setter for createdAt as it's set @PrePersist

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // No public setter for updatedAt as it's set @PrePersist/@PreUpdate

    public Boolean getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(Boolean premium) {
        isPremium = premium;
    }

    public Set<UserDataUpload> getUploads() {
        return uploads;
    }

    public void setUploads(Set<UserDataUpload> uploads) {
        this.uploads = uploads;
    }

    // Helper method to add upload (maintains both sides of relationship)
    public void addUpload(UserDataUpload upload) {
        this.uploads.add(upload);
        upload.setUser(this);
    }

    // Helper method to remove upload (maintains both sides of relationship)
    public void removeUpload(UserDataUpload upload) {
        this.uploads.remove(upload);
        upload.setUser(null); // Disassociate
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        if (this.subscription != null && this.subscription.getUser() == this) {
            this.subscription.setUser(null); // Dissociate old subscription
        }
        this.subscription = subscription;
        if (subscription != null) {
            subscription.setUser(this);
        }
    }

    public Set<PasswordReset> getPasswordResets() {
        return passwordResets;
    }

    public void setPasswordResets(Set<PasswordReset> passwordResets) {
        this.passwordResets = passwordResets;
    }

    // Helper method to add password reset token
    public void addPasswordReset(PasswordReset passwordReset) {
        this.passwordResets.add(passwordReset);
        passwordReset.setUser(this);
    }

    // Helper method to remove password reset token
    public void removePasswordReset(PasswordReset passwordReset) {
        this.passwordResets.remove(passwordReset);
        passwordReset.setUser(null);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isPremium=" + isPremium +
                '}';
    }
}
