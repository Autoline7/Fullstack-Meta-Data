package com.metaWebApp.MetaWebApp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a user's premium subscription details, integrated with Stripe.
 * This entity maps to the 'subscriptions' table.
 */
@Entity
@Table(name = "subscriptions")
public class Subscription {

    /**
     * Unique identifier for each subscription record.
     * Generated automatically as a UUID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    /**
     * The user who owns this subscription.
     * This is a One-to-One relationship, meaning one user has one (or zero) subscription.
     * This side (Subscription) owns the foreign key 'user_id'.
     * The 'user_id' column in the database will store the UUID of the associated User.
     * The unique constraint on 'user_id' ensures a strict one-to-one mapping.
     */
    @OneToOne(fetch = FetchType.LAZY) // Lazy fetching for performance
    @JoinColumn(name = "user_id", nullable = false, unique = true) // Foreign key column, must be unique
    private User user; // The associated User entity

    /**
     * Stripe's unique identifier for this customer. Essential for interacting with Stripe API.
     */
    @Column(name = "stripe_customer_id", nullable = false, unique = true, length = 255)
    private String stripeCustomerId;

    /**
     * Stripe's unique identifier for this specific subscription instance.
     */
    @Column(name = "stripe_subscription_id", nullable = false, unique = true, length = 255)
    private String stripeSubscriptionId;

    /**
     * The type of subscription plan (e.g., 'FREE', 'PREMIUM').
     * Stored as a String, but mapped from an enum for type safety.
     */
    @Enumerated(EnumType.STRING) // Stores the enum name as a string in the DB
    @Column(name = "plan_type", nullable = false, length = 50)
    private PlanType planType;

    /**
     * The current status of the subscription as reported by Stripe.
     * Stored as a String, but mapped from an enum for type safety.
     */
    @Enumerated(EnumType.STRING) // Stores the enum name as a string in the DB
    @Column(name = "status", nullable = false, length = 50)
    private SubscriptionStatus status;

    /**
     * The date and time when the subscription became active.
     */
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    /**
     * The date and time when the subscription is scheduled to end (if canceled or expired).
     * Can be null if the subscription is ongoing.
     */
    @Column(name = "end_date")
    private LocalDateTime endDate;

    /**
     * Timestamp when this subscription record was first created in your database.
     * Automatically set on creation.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Last time this subscription record was updated in your database (e.g., via Stripe webhooks).
     * Automatically updated on any entity modification.
     */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // --- Constructors ---
    public Subscription() {
        // Default constructor required by JPA
    }

    // Convenient constructor for creating new subscriptions
    public Subscription(User user, String stripeCustomerId, String stripeSubscriptionId,
                        PlanType planType, SubscriptionStatus status, LocalDateTime startDate) {
        this.user = user;
        this.stripeCustomerId = stripeCustomerId;
        this.stripeSubscriptionId = stripeSubscriptionId;
        this.planType = planType;
        this.status = status;
        this.startDate = startDate;
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

    // No public setter for auto-generated ID

    public User getUser() {
        return user;
    }

    // Important: For bidirectional @OneToOne, set both sides for consistency
    public void setUser(User user) {
        // Break old relationship if it exists
        if (this.user != null && !this.user.equals(user)) {
            this.user.setSubscription(null);
        }
        this.user = user;
        // Establish new relationship
        if (user != null && !user.getSubscription().equals(this)) {
            user.setSubscription(this);
        }
    }

    public String getStripeCustomerId() {
        return stripeCustomerId;
    }

    public void setStripeCustomerId(String stripeCustomerId) {
        this.stripeCustomerId = stripeCustomerId;
    }

    public String getStripeSubscriptionId() {
        return stripeSubscriptionId;
    }

    public void setStripeSubscriptionId(String stripeSubscriptionId) {
        this.stripeSubscriptionId = stripeSubscriptionId;
    }

    public PlanType getPlanType() {
        return planType;
    }

    public void setPlanType(PlanType planType) {
        this.planType = planType;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // No public setter for createdAt

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // No public setter for updatedAt

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", userId=" + (user != null ? user.getId() : "null") +
                ", stripeCustomerId='" + stripeCustomerId + '\'' +
                ", stripeSubscriptionId='" + stripeSubscriptionId + '\'' +
                ", planType=" + planType +
                ", status=" + status +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

