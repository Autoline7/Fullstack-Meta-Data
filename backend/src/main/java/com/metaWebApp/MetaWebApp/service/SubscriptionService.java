package com.metaWebApp.MetaWebApp.service;

import com.metaWebApp.MetaWebApp.model.PlanType;
import com.metaWebApp.MetaWebApp.model.Subscription;
import com.metaWebApp.MetaWebApp.model.SubscriptionStatus;
import com.metaWebApp.MetaWebApp.model.User;
import com.metaWebApp.MetaWebApp.repository.SubscriptionRepository;
import com.metaWebApp.MetaWebApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for managing Subscription entities.
 * Handles business logic related to user subscriptions and integration with payment systems (like Stripe).
 */
@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository; // To update user's isPremium status

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates or updates a user's subscription record.
     * This is typically called when a user subscribes, or when a webhook from Stripe
     * provides updated subscription information.
     *
     * @param user The user associated with the subscription.
     * @param stripeCustomerId The Stripe Customer ID.
     * @param stripeSubscriptionId The Stripe Subscription ID.
     * @param planType The plan type (e.g., PREMIUM).
     * @param status The current subscription status.
     * @param startDate The subscription start date.
     * @param endDate Optional subscription end date.
     * @return The created or updated Subscription entity.
     */
    @Transactional
    public Subscription createOrUpdateSubscription(User user, String stripeCustomerId,
                                                   String stripeSubscriptionId, PlanType planType,
                                                   SubscriptionStatus status, LocalDateTime startDate,
                                                   LocalDateTime endDate) {
        // Find existing subscription by user ID, or create a new one
        Optional<Subscription> existingSubscription = subscriptionRepository.findByUserId(user.getId());
        Subscription subscription;

        if (existingSubscription.isPresent()) {
            subscription = existingSubscription.get();
        } else {
            subscription = new Subscription();
            subscription.setUser(user); // Set the user for the new subscription
        }

        subscription.setStripeCustomerId(stripeCustomerId);
        subscription.setStripeSubscriptionId(stripeSubscriptionId);
        subscription.setPlanType(planType);
        subscription.setStatus(status);
        subscription.setStartDate(startDate);
        subscription.setEndDate(endDate);

        // Update user's premium status based on subscription status
        boolean isUserPremium = (status == SubscriptionStatus.ACTIVE || status == SubscriptionStatus.TRIALING);
        if (!user.getIsPremium().equals(isUserPremium)) {
            user.setIsPremium(isUserPremium);
            userRepository.save(user); // Save the updated user entity
        }

        return subscriptionRepository.save(subscription);
    }

    /**
     * Retrieves a user's subscription by their user ID.
     *
     * @param userId The UUID of the user.
     * @return An Optional containing the Subscription if found, or empty.
     */
    @Transactional(readOnly = true)
    public Optional<Subscription> getUserSubscription(UUID userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    /**
     * Retrieves a subscription by its Stripe Subscription ID.
     * Useful for webhook processing.
     *
     * @param stripeSubscriptionId The Stripe Subscription ID.
     * @return An Optional containing the Subscription if found, or empty.
     */
    @Transactional(readOnly = true)
    public Optional<Subscription> getSubscriptionByStripeId(String stripeSubscriptionId) {
        return subscriptionRepository.findByStripeSubscriptionId(stripeSubscriptionId);
    }

    /**
     * Marks a subscription as canceled.
     *
     * @param userId The UUID of the user whose subscription is being canceled.
     * @return The updated Subscription entity, or empty Optional if not found.
     */
    @Transactional
    public Optional<Subscription> cancelSubscription(UUID userId) {
        Optional<Subscription> subscriptionOptional = subscriptionRepository.findByUserId(userId);
        subscriptionOptional.ifPresent(subscription -> {
            subscription.setStatus(SubscriptionStatus.CANCELED);
            // Optionally set end date to now or end of current billing period
            // subscription.setEndDate(LocalDateTime.now());
            subscriptionRepository.save(subscription);

            // Also update user's premium status
            User user = subscription.getUser();
            if (user != null && user.getIsPremium()) {
                user.setIsPremium(false);
                userRepository.save(user);
            }
        });
        return subscriptionOptional;
    }
}
