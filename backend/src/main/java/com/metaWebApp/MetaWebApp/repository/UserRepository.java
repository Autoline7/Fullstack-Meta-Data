package com.metaWebApp.MetaWebApp.repository;


import com.metaWebApp.MetaWebApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for the User entity.
 * Provides CRUD operations and custom query methods for User data.
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Finds a User by their email address.
     *
     * @param email The email address to search for.
     * @return An Optional containing the User if found, or empty if not.
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks if a User with the given email address already exists.
     *
     * @param email The email address to check.
     * @return True if a User with this email exists, false otherwise.
     */
    boolean existsByEmail(String email);
}
