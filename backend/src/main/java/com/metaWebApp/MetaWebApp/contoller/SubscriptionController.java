package com.metaWebApp.MetaWebApp.contoller;


import com.metaWebApp.MetaWebApp.dto.SubscriptionRequestDTO;
import com.metaWebApp.MetaWebApp.dto.SubscriptionResponseDTO;
import com.metaWebApp.MetaWebApp.model.Subscription;
import com.metaWebApp.MetaWebApp.model.User;
import com.metaWebApp.MetaWebApp.service.SubscriptionService;
import com.metaWebApp.MetaWebApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final UserService userService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService, UserService userService) {
        this.subscriptionService = subscriptionService;
        this.userService = userService;
    }

    /**
     * Helper method to convert Subscription entity to SubscriptionResponseDTO.
     */
    private SubscriptionResponseDTO convertToDto(Subscription subscription) {
        if (subscription == null) {
            return null;
        }
        return new SubscriptionResponseDTO(
                subscription.getId(),
                subscription.getUser() != null ? subscription.getUser().getId() : null,
                subscription.getPlanType(),
                subscription.getStatus(),
                subscription.getStartDate(),
                subscription.getEndDate(),
                subscription.getCreatedAt(),
                subscription.getUpdatedAt()
        );
    }

    /**
     * Endpoint to retrieve the subscription details for a specific user.
     * Returns SubscriptionResponseDTO.
     *
     * @param userId The UUID of the user.
     * @return ResponseEntity with the SubscriptionResponseDTO or NOT_FOUND status.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<SubscriptionResponseDTO> getUserSubscription(@PathVariable UUID userId) {
        // SECURITY NOTE: Ensure this userId matches the authenticated user, or is an admin request.
        return subscriptionService.getUserSubscription(userId)
                .map(this::convertToDto)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint to create or update a subscription, typically from a webhook or admin panel.
     * Accepts SubscriptionRequestDTO and returns SubscriptionResponseDTO.
     *
     * @param request The DTO containing subscription details.
     * @return ResponseEntity with the created/updated SubscriptionResponseDTO or an error.
     */
    @PostMapping // Or @PutMapping
    public ResponseEntity<SubscriptionResponseDTO> createOrUpdateSubscription(@Valid @RequestBody SubscriptionRequestDTO request) {
        try {
            // Convert DTO to entity fields needed by service
            User user = userService.getUserById(request.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            Subscription subscription = subscriptionService.createOrUpdateSubscription(
                    user,
                    request.getStripeCustomerId(),
                    request.getStripeSubscriptionId(),
                    request.getPlanType(),
                    request.getStatus(),
                    request.getStartDate(),
                    request.getEndDate()
            );
            return new ResponseEntity<>(convertToDto(subscription), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to cancel a user's subscription.
     * Returns the updated SubscriptionResponseDTO.
     *
     * @param userId The UUID of the user whose subscription is to be canceled.
     * @return ResponseEntity with the updated SubscriptionResponseDTO or NOT_FOUND.
     */
    @PutMapping("/user/{userId}/cancel")
    public ResponseEntity<SubscriptionResponseDTO> cancelSubscription(@PathVariable UUID userId) {
        // SECURITY NOTE: Ensure this userId matches the authenticated user, or is an admin request.
        return subscriptionService.cancelSubscription(userId)
                .map(this::convertToDto)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}