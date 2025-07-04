package com.metaWebApp.MetaWebApp.service;

import com.metaWebApp.MetaWebApp.model.PasswordReset;
import com.metaWebApp.MetaWebApp.model.User;
import com.metaWebApp.MetaWebApp.repository.PasswordResetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.security.SecureRandom;
import java.util.Base64; // For token generation

/**
 * Service for managing PasswordReset entities.
 * Handles business logic related to generating, validating, and using password reset tokens.
 */
@Service
public class PasswordResetService {

    private final PasswordResetRepository passwordResetRepository;
    // You might also inject UserRepository if you need to fetch the User directly
    // private final UserRepository userRepository;

    private static final int TOKEN_EXPIRATION_MINUTES = 30; // Token valid for 30 minutes
    private static final int TOKEN_LENGTH_BYTES = 32; // Generates a 32-byte (256-bit) token

    @Autowired
    public PasswordResetService(PasswordResetRepository passwordResetRepository) { //, UserRepository userRepository) {
        this.passwordResetRepository = passwordResetRepository;
        // this.userRepository = userRepository;
    }

    /**
     * Generates a new unique password reset token for a given user.
     * Invalidates any existing unused tokens for that user to prevent multiple active tokens.
     *
     * @param user The User for whom the reset token is being generated.
     * @return The newly created PasswordReset entity.
     */
    @Transactional
    public PasswordReset generateResetToken(User user) {
        // Invalidate any existing unused tokens for this user
        passwordResetRepository.findByUser(user)
                .stream()
                .filter(pr -> !pr.getUsed() && pr.getExpiresAt().isAfter(LocalDateTime.now()))
                .forEach(pr -> {
                    pr.setUsed(true); // Mark as used/invalidated
                    passwordResetRepository.save(pr);
                });

        String token = generateSecureToken();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_MINUTES);

        PasswordReset newReset = new PasswordReset(user, token, expiresAt);
        return passwordResetRepository.save(newReset);
    }

    /**
     * Validates a password reset token.
     * Checks if the token exists, is not used, and has not expired.
     *
     * @param token The token string to validate.
     * @return An Optional containing the valid PasswordReset entity if found, or empty.
     */
    @Transactional(readOnly = true)
    public Optional<PasswordReset> validateResetToken(String token) {
        // Find token that is not used and expires in the future
        return passwordResetRepository.findByTokenAndUsedFalseAndExpiresAtAfter(token, LocalDateTime.now());
    }

    /**
     * Marks a password reset token as used.
     * This should be called after a password has been successfully reset using the token.
     *
     * @param token The token string to mark as used.
     * @throws IllegalArgumentException if the token is not found or already used/expired.
     */
    @Transactional
    public void markTokenAsUsed(String token) {
        PasswordReset passwordReset = passwordResetRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Password reset token not found or invalid."));

        if (passwordReset.getUsed()) {
            throw new IllegalArgumentException("Password reset token has already been used.");
        }
        if (passwordReset.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Password reset token has expired.");
        }

        passwordReset.setUsed(true);
        passwordResetRepository.save(passwordReset);
    }

    /**
     * Generates a cryptographically secure random token string.
     * @return A Base64 encoded secure token.
     */
    private String generateSecureToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[TOKEN_LENGTH_BYTES];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

    /**
     * Deletes a password reset record by its ID.
     * @param id The UUID of the record to delete.
     */
    @Transactional
    public void deleteResetRecord(UUID id) {
        passwordResetRepository.deleteById(id);
    }
}
