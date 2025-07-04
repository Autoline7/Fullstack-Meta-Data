package com.metaWebApp.MetaWebApp.model;

/**
 * Represents the current status of a user's subscription,
 * typically reflecting statuses from payment providers like Stripe.
 */
public enum SubscriptionStatus {
    ACTIVE,         // Subscription is active
    INACTIVE,       // Subscription is not active (could be due to cancellation, expiry, etc.)
    CANCELED,       // Subscription has been canceled by the user or admin
    PAST_DUE,       // Payment for subscription is past due
    TRIALING,       // User is currently in a trial period
    UNPAID,         // Payment attempt failed and is awaiting retry
    COMPLETED       // One-time payment (if you add such functionality)
    // Add more statuses as defined by your payment provider (e.g., 'incomplete', 'incomplete_expired', 'paused')
}
