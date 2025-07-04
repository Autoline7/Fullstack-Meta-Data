package com.metaWebApp.MetaWebApp.service;

import com.metaWebApp.MetaWebApp.model.User;
import com.metaWebApp.MetaWebApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service // Marks this class as a Spring Service component
public class UserService {

    private final UserRepository userRepository;

    // Dependency Injection: Spring will provide an instance of UserRepository
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional // Ensures the method runs within a database transaction
    public User createUser(User user) {
        // Basic validation: check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists.");
        }
        // In a real app, you would hash the password here before saving
        // user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        return userRepository.save(user);
    }

    @Transactional(readOnly = true) // Read-only transactions are optimized for reading
    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(User user) {
        // Here you would typically fetch the existing user, update fields, and save
        Optional<User> existingUserOpt = userRepository.findById(user.getId());
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setEmail(user.getEmail()); // Example: allow email update
            // Don't update passwordHash directly unless it's a specific password change operation
            // existingUser.setPasswordHash(user.getPasswordHash());
            existingUser.setIsPremium(user.getIsPremium()); // Example: allow premium status update (often from SubscriptionService)
            return userRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + user.getId());
        }
    }

    @Transactional
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    // This method is crucial for Spring Security (UserDetailsService implementation)
    // You would adapt this to return Spring Security's UserDetails object
    @Transactional(readOnly = true)
    public Optional<User> findUserByEmailForAuth(String email) {
        return userRepository.findByEmail(email);
    }
}