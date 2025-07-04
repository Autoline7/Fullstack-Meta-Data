package com.metaWebApp.MetaWebApp.model;

/**
 * Defines the type of subscription plan a user is on.
 */
public enum PlanType {
    FREE,
    PREMIUM
    // Add more granular plan types if Stripe supports them (e.g., PREMIUM_MONTHLY, PREMIUM_YEARLY)
}
