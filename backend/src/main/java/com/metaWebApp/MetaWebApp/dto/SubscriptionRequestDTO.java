package com.metaWebApp.MetaWebApp.dto;

import com.metaWebApp.MetaWebApp.model.PlanType;
import com.metaWebApp.MetaWebApp.model.SubscriptionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for creating or updating a Subscription record.
 * Often used internally or via webhook processing, rather than direct user input.
 */
public class SubscriptionRequestDTO {

    @NotNull(message = "User ID cannot be null")
    private UUID userId;

    @NotBlank(message = "Stripe Customer ID cannot be empty")
    private String stripeCustomerId;

    @NotBlank(message = "Stripe Subscription ID cannot be empty")
    private String stripeSubscriptionId;

    @NotNull(message = "Plan type cannot be null")
    private PlanType planType;

    @NotNull(message = "Status cannot be null")
    private SubscriptionStatus status;

    @NotNull(message = "Start date cannot be null")
    private LocalDateTime startDate;

    private LocalDateTime endDate; // Can be null

    // Getters and Setters
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getStripeCustomerId() { return stripeCustomerId; }
    public void setStripeCustomerId(String stripeCustomerId) { this.stripeCustomerId = stripeCustomerId; }
    public String getStripeSubscriptionId() { return stripeSubscriptionId; }
    public void setStripeSubscriptionId(String stripeSubscriptionId) { this.stripeSubscriptionId = stripeSubscriptionId; }
    public PlanType getPlanType() { return planType; }
    public void setPlanType(PlanType planType) { this.planType = planType; }
    public SubscriptionStatus getStatus() { return status; }
    public void setStatus(SubscriptionStatus status) { this.status = status; }
    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
}