package com.metaWebApp.MetaWebApp.repository;

import com.metaWebApp.MetaWebApp.model.Subscription;
import com.metaWebApp.MetaWebApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for the Subscription entity.
 * Provides CRUD operations and custom query methods for Subscription data.
 */
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    /**
     * Finds a Subscription by the associated User.
     *
     * @param user The User entity whose subscription is to be retrieved.
     * @return An Optional containing the Subscription if found, or empty.
     */
    Optional<Subscription> findByUser(User user);

    /**
     * Finds a Subscription by the ID of the associated User.
     *
     * @param userId The UUID of the user.
     * @return An Optional containing the Subscription if found, or empty.
     */
    Optional<Subscription> findByUserId(UUID userId);

    /**
     * Finds a Subscription by its Stripe Customer ID.
     *
     * @param stripeCustomerId The Stripe Customer ID to search for.
     * @return An Optional containing the Subscription if found, or empty.
     */
    Optional<Subscription> findByStripeCustomerId(String stripeCustomerId);

    /**
     * Finds a Subscription by its Stripe Subscription ID.
     *
     * @param stripeSubscriptionId The Stripe Subscription ID to search for.
     * @return An Optional containing the Subscription if found, or empty.
     */
    Optional<Subscription> findByStripeSubscriptionId(String stripeSubscriptionId);
}
