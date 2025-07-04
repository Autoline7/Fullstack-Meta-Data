package com.metaWebApp.MetaWebApp.dto;


import com.metaWebApp.MetaWebApp.model.PlanType;
import com.metaWebApp.MetaWebApp.model.SubscriptionStatus;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for returning Subscription details to the client.
 * May omit sensitive Stripe IDs depending on user role/permissions.
 */
public class SubscriptionResponseDTO {

    private UUID id;
    private UUID userId;
    private PlanType planType;
    private SubscriptionStatus status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // Stripe IDs are omitted here for typical user-facing responses.
    // For admin purposes, a separate DTO or full entity might be used.

    // Default constructor for Jackson
    public SubscriptionResponseDTO() {
    }

    // Constructor to easily convert from Subscription entity
    public SubscriptionResponseDTO(UUID id, UUID userId, PlanType planType,
                                   SubscriptionStatus status, LocalDateTime startDate,
                                   LocalDateTime endDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.planType = planType;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public PlanType getPlanType() { return planType; }
    public void setPlanType(PlanType planType) { this.planType = planType; }
    public SubscriptionStatus getStatus() { return status; }
    public void setStatus(SubscriptionStatus status) { this.status = status; }
    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
