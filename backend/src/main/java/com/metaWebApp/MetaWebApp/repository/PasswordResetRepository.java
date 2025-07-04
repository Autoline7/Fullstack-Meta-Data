package com.metaWebApp.MetaWebApp.repository;

import com.metaWebApp.MetaWebApp.model.PasswordReset;
import com.metaWebApp.MetaWebApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Spring Data JPA repository for the PasswordReset entity.
 * Provides CRUD operations and custom query methods for password reset tokens.
 */
public interface PasswordResetRepository extends JpaRepository<PasswordReset, UUID> {

    /**
     * Finds a PasswordReset token by the token string itself.
     *
     * @param token The unique token string to search for.
     * @return An Optional containing the PasswordReset if found, or empty.
     */
    Optional<PasswordReset> findByToken(String token);

    /**
     * Finds all PasswordReset tokens associated with a specific user.
     *
     * @param user The User entity whose reset tokens are to be retrieved.
     * @return A list of PasswordReset tokens belonging to the user.
     */
    List<PasswordReset> findByUser(User user);

    /**
     * Finds PasswordReset tokens that are not yet used and have not expired.
     * Useful for retrieving active tokens.
     *
     * @param token The token string.
     * @param expiresAt The current time, to find tokens that have not yet expired.
     * @return An Optional containing the active PasswordReset token if found, or empty.
     */
    Optional<PasswordReset> findByTokenAndUsedFalseAndExpiresAtAfter(String token, LocalDateTime expiresAt);
}
